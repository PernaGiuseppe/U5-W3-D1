package giuseppeperna.U5_W3_D1.repositories;

import giuseppeperna.U5_W3_D1.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<Dipendente> findByUsername(String username);

    Optional<Dipendente> findByEmail(String email);
}

