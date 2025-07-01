package topg.paga.harvest.dto;

import topg.paga.enums.CropType;
import topg.paga.enums.Season;

public record HarvestResponse(
        Long id,
        Long farmId,
        String farmName,
        String cropType,
        String season,
        Double actualYieldTons
) {}