package topg.paga.report.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import topg.paga.report.dto.CropSeasonReport;
import topg.paga.report.dto.FarmSeasonReport;
import topg.paga.report.service.IReportService;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final IReportService reportService;

    @GetMapping("/season/farms")
    public ResponseEntity<List<FarmSeasonReport>> getFarmReportsBySeason(
            @RequestParam(name = "season") String season) {
        List<FarmSeasonReport> reports = reportService.getFarmReportsBySeason(season);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/season/crops")
    public ResponseEntity<List<CropSeasonReport>> getCropReportsBySeason(
            @RequestParam(name = "season") String season) {
        List<CropSeasonReport> reports = reportService.getCropReportsBySeason(season);
        return ResponseEntity.ok(reports);
    }
}
