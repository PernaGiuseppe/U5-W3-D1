package giuseppeperna.U5_W3_D1.controllers;

import giuseppeperna.U5_W3_D1.entities.Prenotazione;
import giuseppeperna.U5_W3_D1.payloads.PrenotazioneDTO;
import giuseppeperna.U5_W3_D1.payloads.PrenotazioneResponseDTO;
import giuseppeperna.U5_W3_D1.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @GetMapping
    public Page<PrenotazioneResponseDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return this.prenotazioneService.findAll(page, size, sortBy)
                .map(p -> new PrenotazioneResponseDTO(
                        p.getId(),
                        p.getViaggio().getId(),
                        p.getViaggio().getDestinazione(),
                        p.getDipendente().getId(),
                        p.getDipendente().getNome(),
                        p.getDipendente().getCognome(),
                        p.getDataRichiesta(),
                        p.getNote(),
                        p.getPreferenze()
                ));
    }

    @GetMapping("/{prenotazioneId}")
    public PrenotazioneResponseDTO findById(@PathVariable Long prenotazioneId) {
        Prenotazione p = this.prenotazioneService.findById(prenotazioneId);
        return new PrenotazioneResponseDTO(
                p.getId(),
                p.getViaggio().getId(),
                p.getViaggio().getDestinazione(),
                p.getDipendente().getId(),
                p.getDipendente().getNome(),
                p.getDipendente().getCognome(),
                p.getDataRichiesta(),
                p.getNote(),
                p.getPreferenze()
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PrenotazioneResponseDTO save(@RequestBody @Validated PrenotazioneDTO body) {
        return this.prenotazioneService.save(body);
    }

    @PutMapping("/{prenotazioneId}")
    public PrenotazioneResponseDTO findByIdAndUpdate(@PathVariable Long prenotazioneId, @RequestBody @Validated PrenotazioneDTO body) {
        return this.prenotazioneService.findByIdAndUpdate(prenotazioneId, body);
    }

    @DeleteMapping("/{prenotazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable Long prenotazioneId) {
        this.prenotazioneService.findByIdAndDelete(prenotazioneId);
    }

    @GetMapping("/viaggio/{viaggioId}")
    public List<PrenotazioneResponseDTO> findByViaggioId(@PathVariable Long viaggioId) {
        return this.prenotazioneService.findByViaggioId(viaggioId).stream()
                .map(p -> new PrenotazioneResponseDTO(
                        p.getId(),
                        p.getViaggio().getId(),
                        p.getViaggio().getDestinazione(),
                        p.getDipendente().getId(),
                        p.getDipendente().getNome(),
                        p.getDipendente().getCognome(),
                        p.getDataRichiesta(),
                        p.getNote(),
                        p.getPreferenze()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/dipendente/{dipendenteId}")
    public List<PrenotazioneResponseDTO> findByDipendenteId(@PathVariable Long dipendenteId) {
        return this.prenotazioneService.findByDipendenteId(dipendenteId).stream()
                .map(p -> new PrenotazioneResponseDTO(
                        p.getId(),
                        p.getViaggio().getId(),
                        p.getViaggio().getDestinazione(),
                        p.getDipendente().getId(),
                        p.getDipendente().getNome(),
                        p.getDipendente().getCognome(),
                        p.getDataRichiesta(),
                        p.getNote(),
                        p.getPreferenze()
                ))
                .collect(Collectors.toList());
    }
}
