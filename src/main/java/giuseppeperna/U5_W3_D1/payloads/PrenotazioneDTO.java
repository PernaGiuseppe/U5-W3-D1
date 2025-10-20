package giuseppeperna.U5_W3_D1.payloads;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PrenotazioneDTO(
        @NotNull(message = "L'ID del viaggio è obbligatorio")
        Long viaggioId,

        @NotNull(message = "L'ID del dipendente è obbligatorio")
        Long dipendenteId,

        @NotNull(message = "La data di richiesta è obbligatoria")
        LocalDate dataRichiesta,

        String note,
        String preferenze
) {
}

