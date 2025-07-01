package topg.paga.harvest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import topg.paga.enums.CropType;
import topg.paga.enums.Season;
import topg.paga.farm.model.Farm;
import topg.paga.harvest.dto.HarvestRequest;
import topg.paga.harvest.dto.HarvestResponse;
import topg.paga.harvest.model.Harvest;
import topg.paga.harvest.repository.HarvestRepository;
import topg.paga.farm.repository.FarmRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class HarvestServiceTest {

    private HarvestRepository harvestRepository;
    private FarmRepository farmRepository;
    private HarvestService harvestService;

    private Farm sampleFarm;

    @BeforeEach
    void setUp() {
        harvestRepository = mock(HarvestRepository.class);
        farmRepository = mock(FarmRepository.class);
        harvestService = new HarvestService(harvestRepository, farmRepository);

        sampleFarm = new Farm(1L, "My Farm", new ArrayList<>(), new ArrayList<>());
    }

    @Test
    void testCreateHarvest_success() {
        HarvestRequest request = new HarvestRequest(1L, CropType.CORN, Season.Q1, 4.5);
        Harvest harvest = new Harvest(1L, CropType.CORN, Season.Q1, 4.5, sampleFarm);

        when(farmRepository.findById(1L)).thenReturn(Optional.of(sampleFarm));
        when(harvestRepository.save(any(Harvest.class))).thenReturn(harvest);

        HarvestResponse response = harvestService.createHarvest(request);

        assertThat(response.farmId()).isEqualTo(1L);
        assertThat(response.farmName()).isEqualTo("My Farm");
        assertThat(response.cropType()).isEqualTo("CORN");
        assertThat(response.season()).isEqualTo("Q1");
        assertThat(response.actualYieldTons()).isEqualTo(4.5);
    }

    @Test
    void testGetAllHarvests() {
        List<Harvest> data = List.of(
                new Harvest(1L, CropType.CORN, Season.Q1, 4.5, sampleFarm),
                new Harvest(2L, CropType.POTATO, Season.Q1, 3.2, sampleFarm)
        );

        when(harvestRepository.findAll()).thenReturn(data);

        List<HarvestResponse> results = harvestService.getAllHarvests();

        assertThat(results).hasSize(2);
        assertThat(results.get(0).cropType()).isEqualTo("CORN");
        assertThat(results.get(1).cropType()).isEqualTo("POTATO");
    }

    @Test
    void testGetHarvestsByFarmId() {
        List<Harvest> data = List.of(
                new Harvest(1L, CropType.CORN, Season.Q1, 4.5, sampleFarm)
        );

        when(harvestRepository.findByFarmId(1L)).thenReturn(data);

        List<HarvestResponse> results = harvestService.getHarvestsByFarmId(1L);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).farmName()).isEqualTo("My Farm");
    }

    @Test
    void testGetHarvestsBySeason() {
        List<Harvest> data = List.of(
                new Harvest(1L, CropType.CORN, Season.Q1, 4.5, sampleFarm)
        );

        when(harvestRepository.findBySeason(Season.Q1)).thenReturn(data);

        List<HarvestResponse> results = harvestService.getHarvestsBySeason("q1");

        assertThat(results).hasSize(1);
        assertThat(results.get(0).season()).isEqualTo("Q1");
    }

    @Test
    void testGetHarvestById_found() {
        Harvest harvest = new Harvest(1L, CropType.CORN, Season.Q1, 4.5, sampleFarm);
        when(harvestRepository.findById(1L)).thenReturn(Optional.of(harvest));

        Optional<HarvestResponse> result = harvestService.getHarvestById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().cropType()).isEqualTo("CORN");
    }

    @Test
    void testUpdateHarvest_success() {
        Harvest existing = new Harvest(1L, CropType.CORN, Season.Q1, 4.5, sampleFarm);
        HarvestRequest request = new HarvestRequest(1L, CropType.POTATO, Season.Q2, 3.0);

        when(harvestRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(farmRepository.findById(1L)).thenReturn(Optional.of(sampleFarm));
        when(harvestRepository.save(any(Harvest.class))).thenAnswer(inv -> inv.getArgument(0));

        HarvestResponse response = harvestService.updateHarvest(1L, request);

        assertThat(response.cropType()).isEqualTo("POTATO");
        assertThat(response.season()).isEqualTo("Q2");
        assertThat(response.actualYieldTons()).isEqualTo(3.0);
    }

    @Test
    void testDeleteHarvest_success() {
        Harvest harvest = new Harvest(1L, CropType.CORN, Season.Q1, 4.5, sampleFarm);
        when(harvestRepository.findById(1L)).thenReturn(Optional.of(harvest));

        harvestService.deleteHarvest(1L);

        verify(harvestRepository, times(1)).delete(harvest);
    }
}
