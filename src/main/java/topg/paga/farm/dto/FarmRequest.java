package topg.paga.farm.dto;

import jakarta.validation.constraints.NotBlank;

public record FarmRequest(
        @NotBlank(message = "Farm name is required")
        String name
) {}
