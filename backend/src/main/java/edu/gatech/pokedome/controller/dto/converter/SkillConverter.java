package edu.gatech.pokedome.controller.dto.converter;

import java.util.stream.Collectors;

import edu.gatech.pokedome.controller.dto.SkillDTO;
import edu.gatech.pokedome.pokemon.skill.Skill;

/**
 * Converts a SkillDTO to a Skill entity.
 */
public class SkillConverter {
    public static Skill convertToEntity(final SkillDTO skillDTO) {
        return Skill.builder()
            .name(skillDTO.name())
            .effects(skillDTO.effects().stream()
                    .map(EffectConverter::convertToEntity)
                    .collect(Collectors.toList()))
            .build();
    }
}