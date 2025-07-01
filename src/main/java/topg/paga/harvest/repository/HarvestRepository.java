package topg.paga.harvest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import topg.paga.enums.Season;
import topg.paga.harvest.model.Harvest;
import topg.paga.planting.model.Planting;

import java.util.List;
import java.util.Optional;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest,Long > {
    List<Harvest> findByFarmId(Long farmId);
    List<Harvest> findBySeason(Season season);

}
