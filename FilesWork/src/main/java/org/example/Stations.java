package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class Stations {
    private String name;
    private String number;
    private String line;
    private String date;
    private double depth;

    public Stations(String name, String number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public String toString() {
        return "\t" + this.number + this.name;
    }
}
