package giuseppeperna.U5_W3_D1.payloads;

import giuseppeperna.U5_W3_D1.entities.StatoViaggio;

import java.time.LocalDate;

public record ViaggioResponseDTO(
        Long id,
        String destinazione,
        LocalDate data,
        StatoViaggio stato
) {
}

