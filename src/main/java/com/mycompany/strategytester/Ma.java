package com.mycompany.strategytester;

public class Ma {
    Double value;
    Integer size;

    public Ma(Integer size, Double value) {
        this.value = value;
        this.size = size;
    }

    public int compareValue(Ma other) {
        return this.value.compareTo(other.value);
    }

    boolean above(Ma ma) {
        return this.value > ma.value;
    }

    boolean underneath(Ma ma) {
        return this.value < ma.value;
    }
}
