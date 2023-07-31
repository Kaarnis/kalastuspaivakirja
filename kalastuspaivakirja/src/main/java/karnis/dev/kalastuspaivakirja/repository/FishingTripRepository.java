package karnis.dev.kalastuspaivakirja.repository;

import karnis.dev.kalastuspaivakirja.entity.FishingTripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishingTripRepository extends JpaRepository<FishingTripEntity, Long> {
    // You can add custom methods here if needed
}
