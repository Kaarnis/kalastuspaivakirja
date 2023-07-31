package karnis.dev.kalastuspaivakirja.repository;

import karnis.dev.kalastuspaivakirja.entity.CatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatchRepository extends JpaRepository<CatchEntity, Long> {
}
