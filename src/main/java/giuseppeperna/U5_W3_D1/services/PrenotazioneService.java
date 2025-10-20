package giuseppeperna.U5_W3_D1.services;

import giuseppeperna.U5_W3_D1.entities.Dipendente;
import giuseppeperna.U5_W3_D1.entities.Prenotazione;
import giuseppeperna.U5_W3_D1.entities.Viaggio;
import giuseppeperna.U5_W3_D1.exceptions.BadRequestException;
import giuseppeperna.U5_W3_D1.exceptions.NotFoundException;
import giuseppeperna.U5_W3_D1.payloads.PrenotazioneDTO;
import giuseppeperna.U5_W3_D1.payloads.PrenotazioneResponseDTO;
import giuseppeperna.U5_W3_D1.repositories.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private ViaggioService viaggioService;

    @Autowired
    private DipendenteService dipendenteService;

    public PrenotazioneResponseDTO save(PrenotazioneDTO body) {
        Viaggio viaggio = viaggioService.findById(body.viaggioId());
        Dipendente dipendente = dipendenteService.findById(body.dipendenteId());
        if (prenotazioneRepository.existsByDipendenteIdAndDataRichiesta(body.dipendenteId(), body.dataRichiesta())) {
            throw new BadRequestException("Il dipendente ha già una prenotazione per la data " + body.dataRichiesta());
        }

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setViaggio(viaggio);
        prenotazione.setDipendente(dipendente);
        prenotazione.setDataRichiesta(body.dataRichiesta());
        prenotazione.setNote(body.note());
        prenotazione.setPreferenze(body.preferenze());

        Prenotazione savedPrenotazione = this.prenotazioneRepository.save(prenotazione);
        return new PrenotazioneResponseDTO(
                savedPrenotazione.getId(),
                savedPrenotazione.getViaggio().getId(),
                savedPrenotazione.getViaggio().getDestinazione(),
                savedPrenotazione.getDipendente().getId(),
                savedPrenotazione.getDipendente().getNome(),
                savedPrenotazione.getDipendente().getCognome(),
                savedPrenotazione.getDataRichiesta(),
                savedPrenotazione.getNote(),
                savedPrenotazione.getPreferenze()
        );
    }

    public Page<Prenotazione> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione findById(Long prenotazioneId) {
        return this.prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new NotFoundException("La prenotazione con id " + prenotazioneId + " non è stata trovata"));
    }

    public PrenotazioneResponseDTO findByIdAndUpdate(Long prenotazioneId, PrenotazioneDTO body) {
        Prenotazione found = this.findById(prenotazioneId);

        Viaggio viaggio = viaggioService.findById(body.viaggioId());
        Dipendente dipendente = dipendenteService.findById(body.dipendenteId());
        if (!found.getDipendente().getId().equals(body.dipendenteId()) ||
                !found.getDataRichiesta().equals(body.dataRichiesta())) {
            if (prenotazioneRepository.existsByDipendenteIdAndDataRichiesta(body.dipendenteId(), body.dataRichiesta())) {
                throw new BadRequestException("Il dipendente ha già una prenotazione per la data " + body.dataRichiesta());
            }
        }

        found.setViaggio(viaggio);
        found.setDipendente(dipendente);
        found.setDataRichiesta(body.dataRichiesta());
        found.setNote(body.note());
        found.setPreferenze(body.preferenze());

        Prenotazione updatedPrenotazione = this.prenotazioneRepository.save(found);
        return new PrenotazioneResponseDTO(
                updatedPrenotazione.getId(),
                updatedPrenotazione.getViaggio().getId(),
                updatedPrenotazione.getViaggio().getDestinazione(),
                updatedPrenotazione.getDipendente().getId(),
                updatedPrenotazione.getDipendente().getNome(),
                updatedPrenotazione.getDipendente().getCognome(),
                updatedPrenotazione.getDataRichiesta(),
                updatedPrenotazione.getNote(),
                updatedPrenotazione.getPreferenze()
        );
    }

    public void findByIdAndDelete(Long prenotazioneId) {
        Prenotazione found = this.findById(prenotazioneId);
        this.prenotazioneRepository.delete(found);
    }

    public List<Prenotazione> findByViaggioId(Long viaggioId) {
        return this.prenotazioneRepository.findByViaggioId(viaggioId);
    }

    public List<Prenotazione> findByDipendenteId(Long dipendenteId) {
        return this.prenotazioneRepository.findByDipendenteId(dipendenteId);
    }
}

