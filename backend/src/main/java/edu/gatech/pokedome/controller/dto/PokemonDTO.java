package edu.gatech.pokedome.controller.dto;

import java.io.Serializable;
import java.util.List;

public record PokemonDTO(String nickname,
                         String type,
                         StatGroupDTO baseStat,
                         HitPointStrategyDTO hitPointStrategy,
                         List<SkillDTO> offensiveSkills,
                         List<SkillDTO> defensiveSkills) implements Serializable {
}