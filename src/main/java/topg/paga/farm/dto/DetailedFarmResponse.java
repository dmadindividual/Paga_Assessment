package topg.paga.farm.dto;

import topg.paga.harvest.dto.HarvestResponse;
import topg.paga.planting.dto.PlantingResponse;

import java.util.List;

public record DetailedFarmResponse(
        Long id,
        String name,
        List<PlantingResponse> plantings,
        List<HarvestResponse> harvests
) {}