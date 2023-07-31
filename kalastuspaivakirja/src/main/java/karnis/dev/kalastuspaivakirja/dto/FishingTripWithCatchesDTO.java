package karnis.dev.kalastuspaivakirja.dto;

import karnis.dev.kalastuspaivakirja.entity.CatchEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class FishingTripWithCatchesDTO {

    private Long id;
    private String title;
    private LocalDate date;
    private String location;
    private String weather;
    private String notes;
    private List<CatchEntity> catches;
}
