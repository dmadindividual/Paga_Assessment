package topg.paga.planting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import topg.paga.planting.dto.PlantingRequest;
import topg.paga.planting.dto.PlantingResponse;
import topg.paga.planting.service.IPlantingService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/plantings")
@RequiredArgsConstructor
public class PlantingController {

    private final IPlantingService plantingService;

    @PostMapping
    public ResponseEntity<PlantingResponse> createPlanting(@RequestBody PlantingRequest request) {
        PlantingResponse response = plantingService.createPlanting(request);
        return ResponseEntity.created(URI.create("/api/plantings/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PlantingResponse>> getAllPlantings() {
        return ResponseEntity.ok(plantingService.getAllPlantings());
    }

    @GetMapping("/farm/{farmId}")
    public ResponseEntity<List<PlantingResponse>> getPlantingsByFarm(@PathVariable Long farmId) {
        return ResponseEntity.ok(plantingService.getPlantingsByFarmId(farmId));
    }

    @GetMapping("/season/{season}")
    public ResponseEntity<List<PlantingResponse>> getPlantingsBySeason(@PathVariable String season) {
        return ResponseEntity.ok(plantingService.getPlantingsBySeason(season));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantingResponse> getPlantingById(@PathVariable Long id) {
        return plantingService.getPlantingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantingResponse> updatePlanting(
            @PathVariable Long id,
            @RequestBody PlantingRequest request
    ) {
        PlantingResponse response = plantingService.updatePlanting(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanting(@PathVariable Long id) {
        plantingService.deletePlanting(id);
        return ResponseEntity.noContent().build();
    }
}
