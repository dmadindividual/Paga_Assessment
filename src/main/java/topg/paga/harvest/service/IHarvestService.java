package topg.paga.harvest.service;

import topg.paga.harvest.dto.HarvestRequest;
import topg.paga.harvest.dto.HarvestResponse;

import java.util.List;
import java.util.Optional;

public interface IHarvestService {

    /**
     * Submit a new harvest record.
     */
    HarvestResponse createHarvest(HarvestRequest request);

    /**
     * Get all harvest records.
     */
    List<HarvestResponse> getAllHarvests();

    /**
     * Get all harvests for a specific farm.
     */
    List<HarvestResponse> getHarvestsByFarmId(Long farmId);

    /**
     * Get all harvests for a given season.
     */
    List<HarvestResponse> getHarvestsBySeason(String season);

    /**
     * Optional: Get a specific harvest record by ID.
     */
    Optional<HarvestResponse> getHarvestById(Long id);

    /**
     * Optional: Update a harvest record.
     */
    HarvestResponse updateHarvest(Long id, HarvestRequest request);

    /**
     * Optional: Delete a harvest record.
     */
    void deleteHarvest(Long id);
}

