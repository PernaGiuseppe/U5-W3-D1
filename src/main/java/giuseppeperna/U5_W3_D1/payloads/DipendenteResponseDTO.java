package giuseppeperna.U5_W3_D1.payloads;

public record DipendenteResponseDTO(
        Long id,
        String username,
        String nome,
        String cognome,
        String email,
        String immagineProfilo
) {
}
