package topg.paga.report.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import topg.paga.enums.CropType;
import topg.paga.enums.Season;
import topg.paga.farm.model.Farm;
import topg.paga.harvest.model.Harvest;
import topg.paga.harvest.repository.HarvestRepository;
import topg.paga.planting.model.Planting;
import topg.paga.planting.repository.PlantingRepository;
import topg.paga.report.dto.CropSeasonReport;
import topg.paga.report.dto.FarmSeasonReport;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ReportServiceTest {

    private PlantingRepository plantingRepository;
    private HarvestRepository harvestRepository;
    private ReportService reportService;

    private Farm farmA;

    @BeforeEach
    void setUp() {
        plantingRepository = mock(PlantingRepository.class);
        harvestRepository = mock(HarvestRepository.class);
        reportService = new ReportService(plantingRepository, harvestRepository);

        farmA = new Farm(1L, "Farm A", new ArrayList<>(), new ArrayList<>());
    }

    @Test
    void testGetFarmReportsBySeason() {
        List<Planting> plantings = List.of(
                new Planting(1L, CropType.CORN, Season.Q1, 10.0, 6.0, farmA)
        );
        List<Harvest> harvests = List.of(
                new Harvest(1L, CropType.CORN, Season.Q1, 5.5, farmA)
        );

        when(plantingRepository.findBySeason(Season.Q1)).thenReturn(plantings);
        when(harvestRepository.findBySeason(Season.Q1)).thenReturn(harvests);

        List<FarmSeasonReport> result = reportService.getFarmReportsBySeason("q1");

        assertThat(result).hasSize(1);
        FarmSeasonReport report = result.get(0);
        assertThat(report.farmId()).isEqualTo(1L);
        assertThat(report.farmName()).isEqualTo("Farm A");
        assertThat(report.totalExpectedTons()).isEqualTo(6.0);
        assertThat(report.totalActualTons()).isEqualTo(5.5);
    }

    @Test
    void testGetCropReportsBySeason() {
        List<Planting> plantings = List.of(
                new Planting(1L, CropType.CASSAVA, Season.Q1, 5.0, 2.5, farmA)
        );
        List<Harvest> harvests = List.of(
                new Harvest(1L, CropType.CASSAVA, Season.Q1, 3.5, farmA)
        );

        when(plantingRepository.findBySeason(Season.Q1)).thenReturn(plantings);
        when(harvestRepository.findBySeason(Season.Q1)).thenReturn(harvests);

        List<CropSeasonReport> result = reportService.getCropReportsBySeason("Q1");

        assertThat(result).hasSize(1);
        CropSeasonReport report = result.get(0);
        assertThat(report.cropType()).isEqualTo(CropType.CASSAVA);
        assertThat(report.totalExpectedTons()).isEqualTo(2.5);
        assertThat(report.totalActualTons()).isEqualTo(3.5);
    }
}
