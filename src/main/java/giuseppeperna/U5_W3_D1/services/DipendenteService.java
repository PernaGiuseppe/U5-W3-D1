package giuseppeperna.U5_W3_D1.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import giuseppeperna.U5_W3_D1.entities.Dipendente;
import giuseppeperna.U5_W3_D1.entities.Role;
import giuseppeperna.U5_W3_D1.exceptions.BadRequestException;
import giuseppeperna.U5_W3_D1.exceptions.NotFoundException;
import giuseppeperna.U5_W3_D1.payloads.DipendenteDTO;
import giuseppeperna.U5_W3_D1.payloads.DipendenteResponseDTO;
import giuseppeperna.U5_W3_D1.repositories.DipendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DipendenteService {

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private Cloudinary cloudinaryUploader;

    @Autowired
    private PasswordEncoder bcrypt;

    public DipendenteResponseDTO save(DipendenteDTO body) {
        if (this.dipendenteRepository.existsByUsername(body.username())) {
            throw new BadRequestException("L'username " + body.username() + " è già in uso!");
        }

        if (this.dipendenteRepository.existsByEmail(body.email())) {
            throw new BadRequestException("L'email " + body.email() + " è già in uso!");
        }

        Dipendente dipendente = new Dipendente();
        dipendente.setUsername(body.username());
        dipendente.setNome(body.nome());
        dipendente.setCognome(body.cognome());
        dipendente.setEmail(body.email());
        dipendente.setPassword(bcrypt.encode(body.password()));
        dipendente.setRuolo(Role.USER);
        dipendente.setImmagineProfilo("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());

        Dipendente savedDipendente = this.dipendenteRepository.save(dipendente);

        return new DipendenteResponseDTO(
                savedDipendente.getId(),
                savedDipendente.getUsername(),
                savedDipendente.getNome(),
                savedDipendente.getCognome(),
                savedDipendente.getEmail(),
                savedDipendente.getImmagineProfilo()
        );
    }

    public Page<Dipendente> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.dipendenteRepository.findAll(pageable);
    }

    public Dipendente findById(Long dipendenteId) {
        return this.dipendenteRepository.findById(dipendenteId)
                .orElseThrow(() -> new NotFoundException("Il dipendente con id " + dipendenteId + " non è stato trovato"));
    }

    public Dipendente findByEmail(String email) {
        return this.dipendenteRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Il dipendente con email " + email + " non è stato trovato"));
    }

    public DipendenteResponseDTO findByIdAndUpdate(Long dipendenteId, DipendenteDTO body) {
        Dipendente found = this.findById(dipendenteId);

        if (!found.getUsername().equals(body.username()) && this.dipendenteRepository.existsByUsername(body.username())) {
            throw new BadRequestException("L'username " + body.username() + " è già in uso!");
        }

        if (!found.getEmail().equals(body.email()) && this.dipendenteRepository.existsByEmail(body.email())) {
            throw new BadRequestException("L'email " + body.email() + " è già in uso!");
        }

        found.setUsername(body.username());
        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setEmail(body.email());

        Dipendente updatedDipendente = this.dipendenteRepository.save(found);

        return new DipendenteResponseDTO(
                updatedDipendente.getId(),
                updatedDipendente.getUsername(),
                updatedDipendente.getNome(),
                updatedDipendente.getCognome(),
                updatedDipendente.getEmail(),
                updatedDipendente.getImmagineProfilo()
        );
    }

    public void findByIdAndDelete(Long dipendenteId) {
        Dipendente found = this.findById(dipendenteId);
        this.dipendenteRepository.delete(found);
    }

    public String uploadImmagineProfilo(Long dipendenteId, MultipartFile file) throws IOException {
        Dipendente found = this.findById(dipendenteId);
        String url = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setImmagineProfilo(url);
        this.dipendenteRepository.save(found);
        return url;
    }
}
