package karnis.dev.kalastuspaivakirja.controller;

import karnis.dev.kalastuspaivakirja.entity.CatchEntity;
import karnis.dev.kalastuspaivakirja.service.CatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/fishingtrips/{tripId}/catches")
public class CatchController {
    private final CatchService catchService;

    @PostMapping
    public ResponseEntity<CatchEntity> addCatchToTrip(@PathVariable Long tripId, @RequestBody CatchEntity catchEntity) {
        CatchEntity savedCatch = catchService.addCatchToTrip(tripId, catchEntity);
        return ResponseEntity.ok(savedCatch);
    }



}
