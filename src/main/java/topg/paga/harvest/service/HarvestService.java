package topg.paga.harvest.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import topg.paga.enums.Season;
import topg.paga.farm.model.Farm;
import topg.paga.farm.repository.FarmRepository;
import topg.paga.harvest.dto.HarvestRequest;
import topg.paga.harvest.dto.HarvestResponse;
import topg.paga.harvest.model.Harvest;
import topg.paga.harvest.repository.HarvestRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HarvestService implements IHarvestService {
    private final HarvestRepository harvestRepository;
    private final FarmRepository farmRepository;

    @Override
    @Transactional
    public HarvestResponse createHarvest(HarvestRequest request) {
        Farm farm = farmRepository.findById(request.farmId())
                .orElseThrow(() -> new IllegalArgumentException("Farm not found"));

        Harvest harvest = new Harvest();
        harvest.setFarm(farm);
        harvest.setCropType(request.cropType());
        harvest.setSeason(request.season());
        harvest.setActualYieldTons(request.actualYieldTons());

        Harvest saved = harvestRepository.save(harvest);
        return toResponse(saved);

    }

    @Override
    public List<HarvestResponse> getAllHarvests() {
        return harvestRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<HarvestResponse> getHarvestsByFarmId(Long farmId) {
        return harvestRepository.findByFarmId(farmId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<HarvestResponse> getHarvestsBySeason(String season) {
        Season parsedSeason = Season.valueOf(season.toUpperCase());
        return harvestRepository.findBySeason(parsedSeason)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<HarvestResponse> getHarvestById(Long id) {
        return harvestRepository.findById(id).map(this::toResponse);
    }

    @Override
    public HarvestResponse updateHarvest(Long id, HarvestRequest request) {
        Harvest harvest = harvestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Harvest record not found"));

        Farm farm = farmRepository.findById(request.farmId())
                .orElseThrow(() -> new IllegalArgumentException("Farm not found"));

        harvest.setFarm(farm);
        harvest.setCropType(request.cropType());
        harvest.setSeason(request.season());
        harvest.setActualYieldTons(request.actualYieldTons());

        Harvest updated = harvestRepository.save(harvest);
        return toResponse(updated);
    }

    @Override
    public void deleteHarvest(Long id) {
        Harvest harvest = harvestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Harvest record not found"));

        harvestRepository.delete(harvest);

    }

    private HarvestResponse toResponse(Harvest h) {
        return new HarvestResponse(
                h.getId(),
                h.getFarm().getId(),
                h.getFarm().getName(),
                h.getCropType().toString(),
                h.getSeason().toString(),
                h.getActualYieldTons()
        );
    }
}