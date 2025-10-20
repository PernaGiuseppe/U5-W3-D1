package giuseppeperna.U5_W3_D1.repositories;

import giuseppeperna.U5_W3_D1.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViaggioRepository extends JpaRepository<Viaggio, Long> {
}
