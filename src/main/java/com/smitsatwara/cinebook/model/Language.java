package com.smitsatwara.cinebook.model;

public enum Language {
    ENGLISH(1.3),
    HINDI(1.0),
    GUJARATI(1.0);

    private final double multiplier;

    Language(double multiplier) {
        this.multiplier = multiplier;
    }
    public double getMultiplier() {
        return multiplier;
    }
}
