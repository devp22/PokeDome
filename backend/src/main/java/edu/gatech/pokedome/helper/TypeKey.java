package edu.gatech.pokedome.helper;

import edu.gatech.pokedome.pokemon.PokemonType;

import java.util.Objects;

/**
 * A key for a pair of Pokemon types. Used for storing type effectiveness.
 */
public class TypeKey {

    private final PokemonType type1;
    private final PokemonType type2;

    public TypeKey(final PokemonType type1, final PokemonType type2) {
        this.type1 = type1;
        this.type2 = type2;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final TypeKey that = (TypeKey) o;
        return this.type1 == that.type1 && this.type2 == that.type2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type1, type2);
    }
}
