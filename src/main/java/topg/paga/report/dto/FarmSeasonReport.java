package topg.paga.report.dto;

public record FarmSeasonReport(
        Long farmId,
        String farmName,
        Double totalExpectedTons,
        Double totalActualTons
) {}
