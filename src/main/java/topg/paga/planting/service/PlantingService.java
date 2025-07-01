package topg.paga.planting.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import topg.paga.enums.Season;
import topg.paga.farm.model.Farm;
import topg.paga.farm.repository.FarmRepository;
import topg.paga.planting.dto.PlantingRequest;
import topg.paga.planting.dto.PlantingResponse;
import topg.paga.planting.model.Planting;
import topg.paga.planting.repository.PlantingRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PlantingService implements IPlantingService{
    private final PlantingRepository plantingRepository;
    private final FarmRepository farmRepository;

    @Override
    @Transactional
    public PlantingResponse createPlanting(PlantingRequest request) {
        Farm farm = farmRepository.findById(request.farmId())
                .orElseThrow(() -> new IllegalArgumentException("Farm not found"));

        Planting planting = Planting.builder()
                .farm(farm)
                .cropType(request.cropType())
                .season(request.season())
                .plantingArea(request.plantingArea())
                .expectedYieldTons(request.expectedYieldTons())
                .build();
        Planting saved = plantingRepository.save(planting);

        return toResponse(saved);

    }

    @Override
    public List<PlantingResponse> getAllPlantings() {
        return plantingRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlantingResponse> getPlantingsByFarmId(Long farmId) {
        return plantingRepository.findByFarmId(farmId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


    @Override
    public List<PlantingResponse> getPlantingsBySeason(String season) {
        Season parsedSeason = Season.valueOf(season.toUpperCase());
        return plantingRepository.findBySeason(parsedSeason)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


    @Override
    public Optional<PlantingResponse> getPlantingById(Long id) {
        return plantingRepository.findById(id).map(this::toResponse);
    }

    @Override
    @Transactional
    public PlantingResponse updatePlanting(Long id, PlantingRequest request) {
        Planting planting = plantingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Planting record not found"));

        Farm farm = farmRepository.findById(request.farmId())
                .orElseThrow(() -> new IllegalArgumentException("Farm not found"));

        planting.setFarm(farm);
        planting.setCropType(request.cropType());
        planting.setSeason(request.season());
        planting.setPlantingArea(request.plantingArea());
        planting.setExpectedYieldTons(request.expectedYieldTons());

        Planting updated = plantingRepository.save(planting);
        return toResponse(updated);
    }

    @Override
    @Transactional
    public void deletePlanting(Long id) {
        Planting planting = plantingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Planting record not found"));

        plantingRepository.delete(planting);

    }


    private PlantingResponse toResponse(Planting p) {
        return new PlantingResponse(
                p.getId(),
                p.getFarm().getId(),
                p.getFarm().getName(),
                p.getCropType().toString(),
                p.getSeason().toString(),
                p.getPlantingArea(),
                p.getExpectedYieldTons()
        );
    }
}
