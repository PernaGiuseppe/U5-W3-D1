package giuseppeperna.U5_W3_D1.payloads;

import jakarta.validation.constraints.NotEmpty;

public record DipendenteLoginDTO(
        @NotEmpty(message = "L'email è obbligatoria")
        String email,

        @NotEmpty(message = "La password è obbligatoria")
        String password
) {
}

