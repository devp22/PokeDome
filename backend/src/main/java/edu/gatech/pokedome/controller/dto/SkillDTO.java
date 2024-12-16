package edu.gatech.pokedome.controller.dto;

import java.io.Serializable;
import java.util.List;

public record SkillDTO(String name, List<EffectDTO> effects)
    implements Serializable {
}
