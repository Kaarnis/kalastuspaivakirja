package karnis.dev.kalastuspaivakirja.service;

import karnis.dev.kalastuspaivakirja.entity.CatchEntity;
import karnis.dev.kalastuspaivakirja.entity.FishingTripEntity;
import karnis.dev.kalastuspaivakirja.repository.CatchRepository;
import karnis.dev.kalastuspaivakirja.repository.FishingTripRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CatchService {
    private final CatchRepository catchRepository;
    private final FishingTripRepository fishingTripRepository;

    public CatchEntity addCatchToTrip(Long tripId, CatchEntity catchEntity) {
        // Fetch the corresponding FishingTripEntity using the provided tripId
        Optional<FishingTripEntity> fishingTripOptional = fishingTripRepository.findById(tripId);
        if (fishingTripOptional.isEmpty()) {
            throw new RuntimeException("Fishing trip not found with ID: " + tripId);
        }
        FishingTripEntity fishingTrip = fishingTripOptional.get();

        // Associate the catch with the fetched fishing trip
        catchEntity.setFishingTrip(fishingTrip);
        return catchRepository.save(catchEntity);
    }

}

