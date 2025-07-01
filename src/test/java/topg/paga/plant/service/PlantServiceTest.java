package topg.paga.plant.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import topg.paga.enums.CropType;
import topg.paga.enums.Season;
import topg.paga.farm.model.Farm;
import topg.paga.farm.repository.FarmRepository;
import topg.paga.planting.dto.PlantingRequest;
import topg.paga.planting.dto.PlantingResponse;
import topg.paga.planting.model.Planting;
import topg.paga.planting.repository.PlantingRepository;
import topg.paga.planting.service.PlantingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PlantServiceTest {

    private PlantingRepository plantingRepository;
    private FarmRepository farmRepository;
    private PlantingService plantingService;

    private Farm sampleFarm;

    @BeforeEach
    void setUp() {
        plantingRepository = mock(PlantingRepository.class);
        farmRepository = mock(FarmRepository.class);
        plantingService = new PlantingService(plantingRepository, farmRepository);

        sampleFarm = new Farm(1L, "My Farm", new ArrayList<>(), new ArrayList<>());
    }

    @Test
    void testCreatePlanting_success() {
        PlantingRequest request = new PlantingRequest(1L, CropType.CORN, Season.Q1, 10.0, 5.0);
        Planting planting = new Planting(1L, CropType.CORN, Season.Q1, 10.0, 5.0, sampleFarm);

        when(farmRepository.findById(1L)).thenReturn(Optional.of(sampleFarm));
        when(plantingRepository.save(any(Planting.class))).thenReturn(planting);

        PlantingResponse response = plantingService.createPlanting(request);

        assertThat(response.farmName()).isEqualTo("My Farm");
        assertThat(response.cropType()).isEqualTo(CropType.CORN.toString());
        assertThat(response.expectedYieldTons()).isEqualTo(5.0);
    }

    @Test
    void testGetAllPlantings() {
        List<Planting> data = List.of(
                new Planting(1L, CropType.CORN, Season.Q1, 10.0, 5.0, sampleFarm),
                new Planting(2L, CropType.POTATO, Season.Q2, 8.0, 4.5, sampleFarm)
        );
        when(plantingRepository.findAll()).thenReturn(data);

        List<PlantingResponse> result = plantingService.getAllPlantings();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).cropType()).isEqualTo(CropType.CORN.toString());
    }

    @Test
    void testGetPlantingsByFarmId() {
        when(plantingRepository.findByFarmId(1L)).thenReturn(List.of(
                new Planting(1L, CropType.CORN, Season.Q1, 10.0, 5.0, sampleFarm)
        ));

        List<PlantingResponse> result = plantingService.getPlantingsByFarmId(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).farmId()).isEqualTo(1L);
    }

    @Test
    void testGetPlantingsBySeason() {
        when(plantingRepository.findBySeason(Season.Q1)).thenReturn(List.of(
                new Planting(1L, CropType.CORN, Season.Q1, 10.0, 5.0, sampleFarm)
        ));

        List<PlantingResponse> result = plantingService.getPlantingsBySeason("q1");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).season()).isEqualTo(Season.Q1.toString());
    }

    @Test
    void testGetPlantingById_found() {
        Planting planting = new Planting(1L, CropType.CORN, Season.Q1, 10.0, 5.0, sampleFarm);
        when(plantingRepository.findById(1L)).thenReturn(Optional.of(planting));

        Optional<PlantingResponse> result = plantingService.getPlantingById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().cropType()).isEqualTo(CropType.CORN.toString());
    }

    @Test
    void testUpdatePlanting_success() {
        PlantingRequest request = new PlantingRequest(1L, CropType.POTATO, Season.Q2, 7.0, 3.5);
        Planting existing = new Planting(1L, CropType.CORN, Season.Q1, 10.0, 5.0, sampleFarm);

        when(plantingRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(farmRepository.findById(1L)).thenReturn(Optional.of(sampleFarm));
        when(plantingRepository.save(any(Planting.class))).thenAnswer(inv -> inv.getArgument(0));

        PlantingResponse response = plantingService.updatePlanting(1L, request);

        assertThat(response.cropType()).isEqualTo(CropType.POTATO.toString());
        assertThat(response.season()).isEqualTo(Season.Q2.toString());
        assertThat(response.expectedYieldTons()).isEqualTo(3.5);
    }

    @Test
    void testDeletePlanting_success() {
        Planting planting = new Planting(1L, CropType.CORN, Season.Q1, 10.0, 5.0, sampleFarm);
        when(plantingRepository.findById(1L)).thenReturn(Optional.of(planting));

        plantingService.deletePlanting(1L);

        verify(plantingRepository, times(1)).delete(planting);
    }
}
