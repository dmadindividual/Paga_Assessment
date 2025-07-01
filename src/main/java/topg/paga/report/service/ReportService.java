package topg.paga.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import topg.paga.enums.CropType;
import topg.paga.enums.Season;
import topg.paga.harvest.model.Harvest;
import topg.paga.harvest.repository.HarvestRepository;
import topg.paga.planting.model.Planting;
import topg.paga.planting.repository.PlantingRepository;
import topg.paga.report.dto.CropSeasonReport;
import topg.paga.report.dto.FarmSeasonReport;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService implements IReportService {
    private final PlantingRepository plantingRepository;
    private final HarvestRepository harvestRepository;


    @Override
    public List<FarmSeasonReport> getFarmReportsBySeason(String seasonInput) {
        Season season = Season.valueOf(seasonInput.toUpperCase());

        // Fetch all plantings and harvests for the season
        List<Planting> plantings = plantingRepository.findBySeason(season);
        List<Harvest> harvests = harvestRepository.findBySeason(season);

        // Group expected yield by farm
        Map<Long, Double> expectedByFarm = plantings.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getFarm().getId(),
                        Collectors.summingDouble(Planting::getExpectedYieldTons)
                ));

        // Group actual yield by farm
        Map<Long, Double> actualByFarm = harvests.stream()
                .collect(Collectors.groupingBy(
                        h -> h.getFarm().getId(),
                        Collectors.summingDouble(Harvest::getActualYieldTons)
                ));

        // Merge into final report
        Set<Long> allFarmIds = new HashSet<>();
        allFarmIds.addAll(expectedByFarm.keySet());
        allFarmIds.addAll(actualByFarm.keySet());

        return allFarmIds.stream().map(farmId -> {
            String farmName = plantings.stream()
                    .filter(p -> p.getFarm().getId().equals(farmId))
                    .findFirst()
                    .map(p -> p.getFarm().getName())
                    .orElseGet(() -> harvests.stream()
                            .filter(h -> h.getFarm().getId().equals(farmId))
                            .findFirst()
                            .map(h -> h.getFarm().getName())
                            .orElse("Unknown"));

            return new FarmSeasonReport(
                    farmId,
                    farmName,
                    expectedByFarm.getOrDefault(farmId, 0.0),
                    actualByFarm.getOrDefault(farmId, 0.0)
            );
        }).collect(Collectors.toList());
    }

    @Override
    public List<CropSeasonReport> getCropReportsBySeason(String seasonInput) {
        Season season = Season.valueOf(seasonInput.toUpperCase());

        List<Planting> plantings = plantingRepository.findBySeason(season);
        List<Harvest> harvests = harvestRepository.findBySeason(season);

        var expectedByCrop = plantings.stream()
                .collect(Collectors.groupingBy(
                        Planting::getCropType,
                        Collectors.summingDouble(Planting::getExpectedYieldTons)
                ));

        var actualByCrop = harvests.stream()
                .collect(Collectors.groupingBy(
                        Harvest::getCropType,
                        Collectors.summingDouble(Harvest::getActualYieldTons)
                ));

        Set<CropType> allCropTypes = new HashSet<>();
        allCropTypes.addAll(expectedByCrop.keySet());
        allCropTypes.addAll(actualByCrop.keySet());

        return allCropTypes.stream()
                .map(crop -> new CropSeasonReport(
                        crop,
                        expectedByCrop.getOrDefault(crop, 0.0),
                        actualByCrop.getOrDefault(crop, 0.0)
                ))
                .collect(Collectors.toList());
    }
}
