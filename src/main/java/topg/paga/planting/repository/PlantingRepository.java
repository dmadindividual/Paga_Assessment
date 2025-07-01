package topg.paga.planting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import topg.paga.planting.model.Planting;
import topg.paga.enums.Season;

import java.util.List;

@Repository
public interface PlantingRepository extends JpaRepository<Planting, Long> {

    List<Planting> findByFarmId(Long farmId);

    List<Planting> findBySeason(Season season);
}
