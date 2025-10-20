package giuseppeperna.U5_W3_D1.services;

import giuseppeperna.U5_W3_D1.entities.StatoViaggio;
import giuseppeperna.U5_W3_D1.entities.Viaggio;
import giuseppeperna.U5_W3_D1.exceptions.NotFoundException;
import giuseppeperna.U5_W3_D1.payloads.ViaggioDTO;
import giuseppeperna.U5_W3_D1.payloads.ViaggioResponseDTO;
import giuseppeperna.U5_W3_D1.repositories.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ViaggioService {

    @Autowired
    private ViaggioRepository viaggioRepository;

    public ViaggioResponseDTO save(ViaggioDTO body) {
        Viaggio viaggio = new Viaggio();
        viaggio.setDestinazione(body.destinazione());
        viaggio.setData(body.data());
        viaggio.setStato(body.stato());

        Viaggio savedViaggio = this.viaggioRepository.save(viaggio);
        return new ViaggioResponseDTO(
                savedViaggio.getId(),
                savedViaggio.getDestinazione(),
                savedViaggio.getData(),
                savedViaggio.getStato()
        );
    }

    public Page<Viaggio> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.viaggioRepository.findAll(pageable);
    }

    public Viaggio findById(Long viaggioId) {
        return this.viaggioRepository.findById(viaggioId)
                .orElseThrow(() -> new NotFoundException("Il viaggio con id " + viaggioId + " non è stato trovato"));
    }

    public ViaggioResponseDTO findByIdAndUpdate(Long viaggioId, ViaggioDTO body) {
        Viaggio found = this.findById(viaggioId);

        found.setDestinazione(body.destinazione());
        found.setData(body.data());
        found.setStato(body.stato());

        Viaggio updatedViaggio = this.viaggioRepository.save(found);
        return new ViaggioResponseDTO(
                updatedViaggio.getId(),
                updatedViaggio.getDestinazione(),
                updatedViaggio.getData(),
                updatedViaggio.getStato()
        );
    }

    public void findByIdAndDelete(Long viaggioId) {
        Viaggio found = this.findById(viaggioId);
        this.viaggioRepository.delete(found);
    }

    public ViaggioResponseDTO cambiaStato(Long viaggioId, StatoViaggio nuovoStato) {
        Viaggio found = this.findById(viaggioId);
        found.setStato(nuovoStato);
        Viaggio updatedViaggio = this.viaggioRepository.save(found);
        return new ViaggioResponseDTO(
                updatedViaggio.getId(),
                updatedViaggio.getDestinazione(),
                updatedViaggio.getData(),
                updatedViaggio.getStato()
        );
    }
}
