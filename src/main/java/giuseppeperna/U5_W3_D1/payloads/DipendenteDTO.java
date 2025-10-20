package giuseppeperna.U5_W3_D1.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record DipendenteDTO(
        @NotEmpty(message = "L'username è obbligatorio")
        String username,

        @NotEmpty(message = "Il nome è obbligatorio")
        String nome,

        @NotEmpty(message = "Il cognome è obbligatorio")
        String cognome,

        @NotEmpty(message = "L'email è obbligatoria")
        @Email(message = "Email non valida")
        String email,

        @NotEmpty(message = "La password è obbligatoria")
        @Size(min = 6, message = "La password deve essere di almeno 6 caratteri")
        String password
) {
}

