package edu.gatech.pokedome.pokemon;

public enum PokemonType {
    FIRE,
    WATER,
    GRASS,
    NORMAL;

    /**
     * Converts a string to a PokemonType.
     * @param type the string to convert
     * @return the PokemonType
     */
    public static PokemonType fromString(final String type) {
        for (final PokemonType pokemonType : PokemonType.values()) {
            if (pokemonType.name().equalsIgnoreCase(type)) {
                return pokemonType;
            }
        }
        throw new IllegalArgumentException("No enum constant for type: " + type);
    }
}
