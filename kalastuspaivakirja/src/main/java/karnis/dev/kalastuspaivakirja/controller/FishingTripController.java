package karnis.dev.kalastuspaivakirja.controller;

import karnis.dev.kalastuspaivakirja.dto.FishingTripWithCatchesDTO;
import karnis.dev.kalastuspaivakirja.entity.CatchEntity;
import karnis.dev.kalastuspaivakirja.entity.FishingTripEntity;
import karnis.dev.kalastuspaivakirja.repository.CatchRepository;
import karnis.dev.kalastuspaivakirja.repository.FishingTripRepository;
import karnis.dev.kalastuspaivakirja.service.FishingTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fishingtrips")
@CrossOrigin
public class FishingTripController {

    private final FishingTripRepository fishingTripRepository;
    private final CatchRepository catchRepository;

    @Autowired
    public FishingTripController(FishingTripRepository fishingTripRepository, CatchRepository catchRepository) {
        this.fishingTripRepository = fishingTripRepository;
        this.catchRepository = catchRepository;
    }

    @GetMapping
    public List<FishingTripEntity> getAllFishingTrips() {
        return fishingTripRepository.findAll();
    }

    @PostMapping
    public FishingTripEntity addFishingTrip(@RequestBody FishingTripEntity fishingTrip) {
        for (CatchEntity catchEntity : fishingTrip.getCatches()) {
            catchEntity.setFishingTrip(fishingTrip);
        }
        return fishingTripRepository.save(fishingTrip);
    }

    @PostMapping("/{tripId}/addcatch")
    public FishingTripEntity addCatchToTrip(@PathVariable Long tripId, @RequestBody CatchEntity newCatch) {
        FishingTripEntity fishingTrip = fishingTripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Fishing trip not found with id: " + tripId));
        newCatch.setFishingTrip(fishingTrip);
        catchRepository.save(newCatch);
        fishingTrip.getCatches().add(newCatch);
        return fishingTripRepository.save(fishingTrip);
    }
}
