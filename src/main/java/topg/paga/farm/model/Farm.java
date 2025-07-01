package topg.paga.farm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topg.paga.harvest.model.Harvest;
import topg.paga.planting.model.Planting;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<Planting> plantings;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<Harvest> harvests;
}

