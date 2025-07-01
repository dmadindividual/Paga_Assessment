package topg.paga.farm.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import topg.paga.farm.dto.DetailedFarmResponse;
import topg.paga.farm.dto.FarmRequest;
import topg.paga.farm.dto.FarmResponse;
import topg.paga.farm.model.Farm;
import topg.paga.farm.repository.FarmRepository;
import topg.paga.harvest.dto.HarvestResponse;
import topg.paga.planting.dto.PlantingResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FarmService implements IFarmService{
    private final FarmRepository farmRepository;

    @Override
    @Transactional
    public FarmResponse createFarm(FarmRequest request) {
        if (farmRepository.existsByNameIgnoreCase(request.name())) {
            throw new IllegalArgumentException("Farm with name '" + request.name() + "' already exists");
        }

        Farm farm = new Farm();
        farm.setName(request.name());
        Farm saved = farmRepository.save(farm);
        return new FarmResponse(saved.getId(), saved.getName());
    }

    @Override
    public List<FarmResponse> getAllFarms() {
        return farmRepository.findAll()
                .stream()
                .map(f -> new FarmResponse(f.getId(), f.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FarmResponse> getFarmByName(String name) {
        return farmRepository.findByNameIgnoreCase(name)
                .map(f -> new FarmResponse(f.getId(), f.getName()));
    }

    @Override
    public Optional<FarmResponse> getFarmById(Long id) {
        return farmRepository.findById(id)
                .map(f -> new FarmResponse(f.getId(), f.getName()));
    }

    @Override
    @Transactional
    public FarmResponse updateFarmName(Long id, String newName) {
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Farm not found"));

        if (farmRepository.existsByNameIgnoreCase(newName)) {
            throw new IllegalArgumentException("Farm with name '" + newName + "' already exists");
        }

        farm.setName(newName);
        Farm updated = farmRepository.save(farm);

        return new FarmResponse(updated.getId(), updated.getName());    }

    @Override
    @Transactional
    public void deleteFarm(Long id) {
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Farm not found"));

        if (!farm.getPlantings().isEmpty() || !farm.getHarvests().isEmpty()) {
            throw new IllegalStateException("Cannot delete farm with existing planting or harvest records");
        }

        farmRepository.delete(farm);

    }

    @Override
    public DetailedFarmResponse getFullFarmDetails(Long farmId) {
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new IllegalArgumentException("Farm not found"));

        List<PlantingResponse> plantings = farm.getPlantings()
                .stream()
                .map(p -> new PlantingResponse(
                        p.getId(),
                        p.getFarm().getId(),
                        p.getFarm().getName(),
                        p.getCropType().name(),
                        p.getSeason().name(),
                        p.getPlantingArea(),
                        p.getExpectedYieldTons()))
                .collect(Collectors.toList());

        List<HarvestResponse> harvests = farm.getHarvests()
                .stream()
                .map(h -> new HarvestResponse(
                        h.getId(),
                        h.getFarm().getId(),
                        h.getFarm().getName(),
                        h.getCropType().name(),
                        h.getSeason().name(),
                        h.getActualYieldTons()))
                .collect(Collectors.toList());

        return new DetailedFarmResponse(farm.getId(), farm.getName(), plantings, harvests);
    }
}
