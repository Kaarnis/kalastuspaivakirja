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
import java.util.Optional;

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
    public ResponseEntity<FishingTripEntity> addCatchToTrip(@PathVariable Long tripId, @RequestBody CatchEntity newCatch) {
        FishingTripEntity fishingTrip = fishingTripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Fishing trip not found with id: " + tripId));
        newCatch.setFishingTrip(fishingTrip);
        catchRepository.save(newCatch);
        fishingTrip.getCatches().add(newCatch);
        return ResponseEntity.ok(fishingTripRepository.save(fishingTrip));
    }


    @DeleteMapping("/{tripId}/catches/{catchId}")
    public ResponseEntity<?> deleteCatchFromTrip(@PathVariable Long tripId, @PathVariable Long catchId) {
        FishingTripEntity fishingTrip = fishingTripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Fishing trip not found with id: " + tripId));

        // Find the catch to delete
        CatchEntity catchToDelete = fishingTrip.getCatches().stream()
                .filter(catchItem -> catchItem.getId().equals(catchId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Catch not found with id: " + catchId));

        // Remove the catch from the list and save the updated fishing trip
        fishingTrip.getCatches().remove(catchToDelete);
        fishingTripRepository.save(fishingTrip);

        // Delete the catch from the database
        catchRepository.deleteById(catchId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{tripId}")
    public ResponseEntity<?> deleteFishingTrip(@PathVariable Long tripId) {
        Optional<FishingTripEntity> optionalFishingTrip = fishingTripRepository.findById(tripId);
        if (optionalFishingTrip.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        FishingTripEntity fishingTrip = optionalFishingTrip.get();

        // Delete the catches associated with the fishing trip
        catchRepository.deleteAll(fishingTrip.getCatches());

        // Delete the fishing trip from the database
        fishingTripRepository.deleteById(tripId);

        String message = "Fishing trip with ID " + tripId + " and associated catches have been deleted.";
        return ResponseEntity.ok().body(message);
    }

}
