package topg.paga.farm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import topg.paga.farm.dto.DetailedFarmResponse;
import topg.paga.farm.dto.FarmRequest;
import topg.paga.farm.dto.FarmResponse;
import topg.paga.farm.service.IFarmService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/farms")
@RequiredArgsConstructor
public class FarmController {

    private final IFarmService farmService;

    @PostMapping
    public ResponseEntity<FarmResponse> createFarm(@RequestBody FarmRequest request) {
        FarmResponse created = farmService.createFarm(request);
        return ResponseEntity.created(URI.create("/api/farms/" + created.id())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<FarmResponse>> getAllFarms() {
        return ResponseEntity.ok(farmService.getAllFarms());
    }

    @GetMapping("/name")
    public ResponseEntity<FarmResponse> getFarmByName(@RequestParam String name) {
        return farmService.getFarmByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<FarmResponse> getFarmById(@PathVariable Long id) {
        return farmService.getFarmById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FarmResponse> updateFarmName(@PathVariable Long id, @RequestParam String newName) {
        return ResponseEntity.ok(farmService.updateFarmName(id, newName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarm(@PathVariable Long id) {
        farmService.deleteFarm(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<DetailedFarmResponse> getFullFarmDetails(@PathVariable Long id) {
        return ResponseEntity.ok(farmService.getFullFarmDetails(id));
    }
}
