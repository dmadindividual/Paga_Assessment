package topg.paga.farm.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import topg.paga.farm.dto.FarmRequest;
import topg.paga.farm.dto.FarmResponse;
import topg.paga.farm.model.Farm;
import topg.paga.farm.repository.FarmRepository;
import topg.paga.planting.model.Planting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FarmServiceTest {
    private FarmRepository farmRepository;
    private FarmService farmService;

    @BeforeEach
    void setUp() {
        farmRepository = mock(FarmRepository.class);
        farmService = new FarmService(farmRepository);
    }

    @Test
    void testCreateFarm_success() {
        FarmRequest request = new FarmRequest("Green Farm");
        Farm savedFarm = new Farm(1L, "Green Farm", new ArrayList<>(), new ArrayList<>());

        when(farmRepository.existsByNameIgnoreCase("Green Farm")).thenReturn(false);
        when(farmRepository.save(any(Farm.class))).thenReturn(savedFarm);

        FarmResponse response = farmService.createFarm(request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Green Farm");
    }

    @Test
    void testCreateFarm_duplicateName_throwsException() {
        when(farmRepository.existsByNameIgnoreCase("Green Farm")).thenReturn(true);
        FarmRequest request = new FarmRequest("Green Farm");

        assertThatThrownBy(() -> farmService.createFarm(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void testGetFarmById_found() {
        Farm farm = new Farm(1L, "Sky Farm", new ArrayList<>(), new ArrayList<>());
        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));

        Optional<FarmResponse> result = farmService.getFarmById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().name()).isEqualTo("Sky Farm");
    }

    @Test
    void testGetAllFarms() {
        List<Farm> farms = List.of(
                new Farm(1L, "A Farm", new ArrayList<>(), new ArrayList<>()),
                new Farm(2L, "B Farm", new ArrayList<>(), new ArrayList<>())
        );
        when(farmRepository.findAll()).thenReturn(farms);

        List<FarmResponse> results = farmService.getAllFarms();

        assertThat(results).hasSize(2);
        assertThat(results.get(0).name()).isEqualTo("A Farm");
    }

    @Test
    void testUpdateFarmName_success() {
        Farm existing = new Farm(1L, "Old Name", new ArrayList<>(), new ArrayList<>());
        when(farmRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(farmRepository.existsByNameIgnoreCase("New Name")).thenReturn(false);
        when(farmRepository.save(any(Farm.class))).thenAnswer(i -> i.getArgument(0));

        FarmResponse response = farmService.updateFarmName(1L, "New Name");

        assertThat(response.name()).isEqualTo("New Name");
    }

    @Test
    void testDeleteFarm_success() {
        Farm farm = new Farm(1L, "Delete Farm", new ArrayList<>(), new ArrayList<>());
        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));

        farmService.deleteFarm(1L);

        verify(farmRepository, times(1)).delete(farm);
    }

    @Test
    void testDeleteFarm_withPlantingOrHarvest_throwsException() {
        Farm farm = new Farm(1L, "Busy Farm",
                List.of(mock(Planting.class)), List.of());

        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));

        assertThatThrownBy(() -> farmService.deleteFarm(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Cannot delete farm");
    }
}
