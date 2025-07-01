package topg.paga.planting.dto;

import topg.paga.enums.CropType;
import topg.paga.enums.Season;

public record PlantingResponse(
        Long id,
        Long farmId,
        String farmName,
        String cropType,
        String season,
        Double plantingArea,
        Double expectedYieldTons
) {}