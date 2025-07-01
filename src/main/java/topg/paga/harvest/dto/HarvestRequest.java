package topg.paga.harvest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import topg.paga.enums.CropType;
import topg.paga.enums.Season;

public record HarvestRequest(

        @NotNull(message = "Farm ID is required")
        Long farmId,

        @NotNull(message = "Crop type is required")
        CropType cropType,

        @NotNull(message = "Season is required")
        Season season,

        @NotNull(message = "Actual yield is required")
        @Min(value = 0, message = "Actual yield must be non-negative")
        Double actualYieldTons

) {}