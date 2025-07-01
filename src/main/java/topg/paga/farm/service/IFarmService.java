package topg.paga.farm.service;

import topg.paga.farm.dto.DetailedFarmResponse;
import topg.paga.farm.dto.FarmRequest;
import topg.paga.farm.dto.FarmResponse;

import java.util.List;
import java.util.Optional;

public interface IFarmService {


    FarmResponse createFarm(FarmRequest request);


    List<FarmResponse> getAllFarms();


    Optional<FarmResponse> getFarmByName(String name);


    Optional<FarmResponse> getFarmById(Long id);

    FarmResponse updateFarmName(Long id, String newName);


    void deleteFarm(Long id);

    DetailedFarmResponse getFullFarmDetails(Long farmId);

}
