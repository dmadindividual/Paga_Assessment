package topg.paga.farm.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import topg.paga.farm.model.Farm;

import java.util.Optional;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {
    boolean existsByNameIgnoreCase(String name);

    Optional<Farm> findByNameIgnoreCase(String name);
}
