package topg.paga.planting.service;

import topg.paga.planting.dto.PlantingRequest;
import topg.paga.planting.dto.PlantingResponse;

import java.util.List;
import java.util.Optional;

public interface IPlantingService {


    PlantingResponse createPlanting(PlantingRequest request);


    List<PlantingResponse> getAllPlantings();


    List<PlantingResponse> getPlantingsByFarmId(Long farmId);


    List<PlantingResponse> getPlantingsBySeason(String season);


    Optional<PlantingResponse> getPlantingById(Long id);


    PlantingResponse updatePlanting(Long id, PlantingRequest request);


    void deletePlanting(Long id);
}
