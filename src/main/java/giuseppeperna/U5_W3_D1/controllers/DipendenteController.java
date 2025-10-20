package giuseppeperna.U5_W3_D1.controllers;

import giuseppeperna.U5_W3_D1.entities.Dipendente;
import giuseppeperna.U5_W3_D1.exceptions.BadRequestException;
import giuseppeperna.U5_W3_D1.payloads.DipendenteDTO;
import giuseppeperna.U5_W3_D1.payloads.DipendenteResponseDTO;
import giuseppeperna.U5_W3_D1.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/dipendenti")
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;

    @GetMapping
    public Page<Dipendente> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return this.dipendenteService.findAll(page, size, sortBy);
    }

    @GetMapping("/{dipendenteId}")
    public Dipendente findById(@PathVariable Long dipendenteId) {
        return this.dipendenteService.findById(dipendenteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DipendenteResponseDTO save(@RequestBody @Validated DipendenteDTO body) {
        return this.dipendenteService.save(body);
    }

    @PutMapping("/{dipendenteId}")
    public DipendenteResponseDTO findByIdAndUpdate(@PathVariable Long dipendenteId, @RequestBody @Validated DipendenteDTO body) {
        return this.dipendenteService.findByIdAndUpdate(dipendenteId, body);
    }

    @DeleteMapping("/{dipendenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable Long dipendenteId) {
        this.dipendenteService.findByIdAndDelete(dipendenteId);
    }

    @PatchMapping("/{dipendenteId}/immagine")
    public String uploadImmagineProfilo(@PathVariable Long dipendenteId, @RequestParam("immagine") MultipartFile file) {
        try {
            return this.dipendenteService.uploadImmagineProfilo(dipendenteId, file);
        } catch (IOException e) {
            throw new BadRequestException("Errore durante l'upload dell'immagine: " + e.getMessage());
        }
    }

}

