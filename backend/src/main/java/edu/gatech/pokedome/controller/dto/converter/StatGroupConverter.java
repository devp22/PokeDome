package edu.gatech.pokedome.controller.dto.converter;

import edu.gatech.pokedome.controller.dto.StatGroupDTO;
import edu.gatech.pokedome.pokemon.statGroup.StatGroup;

/**
 * Converts a StatGroupDTO to a StatGroup entity.
 */
public class StatGroupConverter {
    public static StatGroup convertToEntity(final StatGroupDTO statGroupDTO) {
        return StatGroup.builder()
            .hitPoints(statGroupDTO.hitPoints())
            .attack(statGroupDTO.attack())
            .defense(statGroupDTO.defense())
            .build();
    }
}
