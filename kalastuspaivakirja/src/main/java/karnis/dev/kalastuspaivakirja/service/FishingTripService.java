package karnis.dev.kalastuspaivakirja.service;

import jakarta.persistence.EntityNotFoundException;
import karnis.dev.kalastuspaivakirja.dto.FishingTripWithCatchesDTO;
import karnis.dev.kalastuspaivakirja.entity.CatchEntity;
import karnis.dev.kalastuspaivakirja.entity.FishingTripEntity;
import karnis.dev.kalastuspaivakirja.repository.CatchRepository;
import karnis.dev.kalastuspaivakirja.repository.FishingTripRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FishingTripService {
    private final FishingTripRepository fishingTripRepository;
    private final CatchRepository catchRepository;

    // Methods to handle business logic related to fishing trips
    public List<FishingTripWithCatchesDTO> getAllFishingTripsWithCatches() {
        List<FishingTripEntity> fishingTrips = fishingTripRepository.findAll();
        List<FishingTripWithCatchesDTO> dtos = new ArrayList<>();

        for (FishingTripEntity fishingTrip : fishingTrips) {
            FishingTripWithCatchesDTO dto = new FishingTripWithCatchesDTO();
            dto.setId(fishingTrip.getId());
            dto.setTitle(fishingTrip.getTitle());
            dto.setDate(fishingTrip.getDate());
            dto.setLocation(fishingTrip.getLocation());
            dto.setWeather(fishingTrip.getWeather());
            dto.setNotes(fishingTrip.getNotes());
            dto.setCatches(fishingTrip.getCatches()); // Assuming getCatches() returns the list of catches

            dtos.add(dto);
        }
        return dtos;
    }
    public FishingTripWithCatchesDTO getFishingTripWithCatches(Long tripId) {
        Optional<FishingTripEntity> optionalFishingTrip = fishingTripRepository.findById(tripId);

        if (optionalFishingTrip.isEmpty()) {
            // Handle the case where the fishing trip with the given id is not found
            throw new EntityNotFoundException("Fishing trip not found with id: " + tripId);
        }

        FishingTripEntity fishingTrip = optionalFishingTrip.get();
        FishingTripWithCatchesDTO dto = new FishingTripWithCatchesDTO();
        dto.setId(fishingTrip.getId());
        dto.setTitle(fishingTrip.getTitle());
        dto.setDate(fishingTrip.getDate());
        dto.setLocation(fishingTrip.getLocation());
        dto.setWeather(fishingTrip.getWeather());
        dto.setNotes(fishingTrip.getNotes());
        dto.setCatches(fishingTrip.getCatches()); // Assuming getCatches() returns the list of catches

        return dto;
    }

    public FishingTripEntity saveFishingTripWithCatches(FishingTripEntity fishingTripEntity, List<CatchEntity> catches) {
        fishingTripEntity.setCatches(catches); // Set the relationship between fishing trip and catches
        return fishingTripRepository.save(fishingTripEntity);
    }


    // Other methods for creating, updating, and deleting fishing trips as needed

}