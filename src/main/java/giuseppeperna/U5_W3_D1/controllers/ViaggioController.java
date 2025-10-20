package giuseppeperna.U5_W3_D1.controllers;

import giuseppeperna.U5_W3_D1.entities.StatoViaggio;
import giuseppeperna.U5_W3_D1.entities.Viaggio;
import giuseppeperna.U5_W3_D1.payloads.ViaggioDTO;
import giuseppeperna.U5_W3_D1.payloads.ViaggioResponseDTO;
import giuseppeperna.U5_W3_D1.services.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/viaggi")
public class ViaggioController {

    @Autowired
    private ViaggioService viaggioService;

    @GetMapping
    public Page<Viaggio> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return this.viaggioService.findAll(page, size, sortBy);
    }

    @GetMapping("/{viaggioId}")
    public Viaggio findById(@PathVariable Long viaggioId) {
        return this.viaggioService.findById(viaggioId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ViaggioResponseDTO save(@RequestBody @Validated ViaggioDTO body) {
        return this.viaggioService.save(body);
    }

    @PutMapping("/{viaggioId}")
    public ViaggioResponseDTO findByIdAndUpdate(@PathVariable Long viaggioId, @RequestBody @Validated ViaggioDTO body) {
        return this.viaggioService.findByIdAndUpdate(viaggioId, body);
    }

    @DeleteMapping("/{viaggioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable Long viaggioId) {
        this.viaggioService.findByIdAndDelete(viaggioId);
    }

    @PatchMapping("/{viaggioId}/stato")
    public ViaggioResponseDTO cambiaStato(
            @PathVariable Long viaggioId,
            @RequestParam StatoViaggio nuovoStato
    ) {
        return this.viaggioService.cambiaStato(viaggioId, nuovoStato);
    }
}
