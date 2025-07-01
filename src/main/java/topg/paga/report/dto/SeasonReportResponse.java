package topg.paga.report.dto;

public record SeasonReportResponse(
        String farmName,
        String cropType,
        double expectedYield,
        double actualYield
) {}
