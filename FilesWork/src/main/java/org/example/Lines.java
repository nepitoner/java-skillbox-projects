package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Lines {
    private String name;
    private String number;

    @Override
    public String toString() {
        return "Линия: " + this.name + " (" + this.number + ")";
    }
}
