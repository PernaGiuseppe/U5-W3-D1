package giuseppeperna.U5_W3_D1.payloads;

import jakarta.validation.constraints.NotNull;

public record AssegnazioneDTO(
        @NotNull(message = "L'ID del dipendente è obbligatorio")
        Long dipendenteId
) {
}

