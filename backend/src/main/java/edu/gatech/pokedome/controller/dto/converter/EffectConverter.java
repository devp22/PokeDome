package edu.gatech.pokedome.controller.dto.converter;

import edu.gatech.pokedome.controller.dto.EffectDTO;
import edu.gatech.pokedome.pokemon.effect.*;

/**
 * Converts an EffectDTO to an Effect entity.
 */
public class EffectConverter {
    public static Effect convertToEntity(final EffectDTO effectDTO) {
        return switch (effectDTO.effectType()) {
            case "DamageEffect" -> DamageEffect.builder().damage(effectDTO.damage()).type(effectDTO.dmgType()).build();
            case "ParalysisEffect" -> ParalysisEffect.builder().turnsActive(effectDTO.turnsActive()).build();
            case "DamageOverTimeEffect" ->
                    DamageOverTimeEffect.builder().damage(effectDTO.damage()).turnsActive(effectDTO.turnsActive()).build();
            case "StatChangeEffect" ->
                    StatChangeEffect.builder().defenseChange(effectDTO.defenseChange()).attackChange(effectDTO.attackChange()).turnsActive(effectDTO.turnsActive()).build();
            case "HealEffect" ->
                    HealEffect.builder().heal(effectDTO.heal()).build();
            case "CopyEffect" ->
                    CopyEffect.builder().build();
            default -> throw new IllegalArgumentException("Invalid effect type: " + effectDTO.effectType());
        };
    }
}
