package edu.gatech.pokedome.controller.dto.converter;

import java.util.List;

import edu.gatech.pokedome.controller.dto.PokemonDTO;
import edu.gatech.pokedome.pokemon.Pokemon;
import edu.gatech.pokedome.pokemon.PokemonType;
import edu.gatech.pokedome.pokemon.skill.Skill;
import edu.gatech.pokedome.pokemon.strategy.HitPointStrategy;

/**
 * Converts a PokemonDTO to a Pokemon entity.
 * PokemonDTOs are used to represent Pokemon in the API, while Pokemon entities are used in the backend.
 */
public class PokemonConverter {
    public Pokemon convertToEntity(final PokemonDTO pokemonDTO) {
        final List<Skill> offensiveSkills = pokemonDTO.offensiveSkills().stream()
            .map(SkillConverter::convertToEntity)
            .toList();
        final List<Skill> defensiveSkills = pokemonDTO.defensiveSkills().stream()
            .map(SkillConverter::convertToEntity)
            .toList();

        return new Pokemon(pokemonDTO.nickname(),
            PokemonType.fromString(pokemonDTO.type()),
            new HitPointStrategy(pokemonDTO.hitPointStrategy().aggression(), pokemonDTO.hitPointStrategy().caution()),
            StatGroupConverter.convertToEntity(pokemonDTO.baseStat()),
            offensiveSkills,
            defensiveSkills);
    }
}
