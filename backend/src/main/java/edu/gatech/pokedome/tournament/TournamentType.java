package edu.gatech.pokedome.tournament;

import lombok.Getter;

@Getter
public enum TournamentType {
    SINGLE_ELIMINATION("SingleElimination"),
    DOUBLE_ELIMINATION("DoubleElimination"),
    ROUND_ROBIN("RoundRobin");

    private final String value;

    TournamentType(final String value) {
        this.value = value;
    }

    public static TournamentType fromString(final String type) {
        return switch (type.toLowerCase()) {
            case "singleelimination" -> SINGLE_ELIMINATION;
            case "doubleelimination" -> DOUBLE_ELIMINATION;
            case "roundrobin" -> ROUND_ROBIN;
            default -> throw new IllegalArgumentException("No enum constant for type: " + type);
        };
    }
}
