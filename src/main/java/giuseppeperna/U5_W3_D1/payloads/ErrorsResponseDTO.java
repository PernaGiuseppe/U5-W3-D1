package giuseppeperna.U5_W3_D1.payloads;

import java.time.LocalDateTime;

public record ErrorsResponseDTO(
        String message,
        LocalDateTime timestamp
) {
}
