package edu.gatech.pokedome.controller.dto;

import edu.gatech.pokedome.pokemon.PokemonType;

import java.io.Serializable;

public record EffectDTO(String effectType,
                        int damage,
                        int attackChange,
                        int defenseChange,
                        int turnsActive,
                        PokemonType dmgType,
                        int heal) implements Serializable {
}