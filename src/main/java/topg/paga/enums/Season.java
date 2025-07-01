package topg.paga.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Season {
    Q1("January - March"),
    Q2("April - June"),
    Q3("July - September"),
    Q4("October - December"),
    WET_SEASON("Wet Season"),
    DRY_SEASON("Dry Season");

    private final String description;

}

