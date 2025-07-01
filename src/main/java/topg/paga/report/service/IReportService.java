package topg.paga.report.service;

import topg.paga.report.dto.CropSeasonReport;
import topg.paga.report.dto.FarmSeasonReport;

import java.util.List;

public interface IReportService {

    List<FarmSeasonReport> getFarmReportsBySeason(String seasonInput);


    List<CropSeasonReport> getCropReportsBySeason(String seasonInput);
}
