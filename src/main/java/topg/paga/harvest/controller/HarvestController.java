package topg.paga.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import topg.paga.harvest.dto.HarvestRequest;
import topg.paga.harvest.dto.HarvestResponse;
import topg.paga.harvest.service.IHarvestService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/harvests")
@RequiredArgsConstructor
public class HarvestController {

    private final IHarvestService harvestService;

    @PostMapping
    public ResponseEntity<HarvestResponse> createHarvest(@RequestBody HarvestRequest request) {
        HarvestResponse response = harvestService.createHarvest(request);
        return ResponseEntity.created(URI.create("/api/harvests/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<HarvestResponse>> getAllHarvests() {
        return ResponseEntity.ok(harvestService.getAllHarvests());
    }

    @GetMapping("/farm/{farmId}")
    public ResponseEntity<List<HarvestResponse>> getHarvestsByFarm(@PathVariable Long farmId) {
        return ResponseEntity.ok(harvestService.getHarvestsByFarmId(farmId));
    }

    @GetMapping("/season/{season}")
    public ResponseEntity<List<HarvestResponse>> getHarvestsBySeason(@PathVariable String season) {
        return ResponseEntity.ok(harvestService.getHarvestsBySeason(season));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HarvestResponse> getHarvestById(@PathVariable Long id) {
        return harvestService.getHarvestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HarvestResponse> updateHarvest(
            @PathVariable Long id,
            @RequestBody HarvestRequest request
    ) {
        HarvestResponse response = harvestService.updateHarvest(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHarvest(@PathVariable Long id) {
        harvestService.deleteHarvest(id);
        return ResponseEntity.noContent().build();
    }
}
