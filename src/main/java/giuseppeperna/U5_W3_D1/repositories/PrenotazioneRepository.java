package giuseppeperna.U5_W3_D1.repositories;

import giuseppeperna.U5_W3_D1.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    boolean existsByDipendenteIdAndDataRichiesta(Long dipendenteId, LocalDate dataRichiesta);

    List<Prenotazione> findByViaggioId(Long viaggioId);

    List<Prenotazione> findByDipendenteId(Long dipendenteId);
}

