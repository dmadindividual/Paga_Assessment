package topg.paga.harvest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import topg.paga.enums.CropType;
import topg.paga.enums.Season;
import topg.paga.farm.model.Farm;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Harvest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CropType cropType;

    @Enumerated(EnumType.STRING)
    private Season season;

    private Double actualYieldTons;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;
}

