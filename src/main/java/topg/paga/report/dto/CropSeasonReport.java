package topg.paga.report.dto;

import topg.paga.enums.CropType;

public record CropSeasonReport(
        CropType cropType,
        Double totalExpectedTons,
        Double totalActualTons
) {}