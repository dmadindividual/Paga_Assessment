package topg.paga.planting.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import topg.paga.enums.CropType;
import topg.paga.enums.Season;

public record PlantingRequest(

        @NotNull(message = "Farm ID is required")
        Long farmId,

        @NotNull(message = "Crop type is required")
        CropType cropType,

        @NotNull(message = "Season is required")
        Season season,

        @NotNull(message = "Planting area is required")
        @Min(value = 0, message = "Planting area must be non-negative")
        Double plantingArea,

        @NotNull(message = "Expected yield is required")
        @Min(value = 0, message = "Expected yield must be non-negative")
        Double expectedYieldTons

) {}
