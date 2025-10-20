package giuseppeperna.U5_W3_D1.payloads;

import java.time.LocalDate;

public record PrenotazioneResponseDTO(
        Long id,
        Long viaggioId,
        String destinazioneViaggio,
        Long dipendenteId,
        String nomeDipendente,
        String cognomeDipendente,
        LocalDate dataRichiesta,
        String note,
        String preferenze
) {
}

