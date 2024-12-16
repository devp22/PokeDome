package edu.gatech.pokedome.pokemon.strategy;

import edu.gatech.pokedome.pokemon.skill.Skill;
import edu.gatech.pokedome.pokemon.Pokemon;

public interface SkillStrategy {
    Skill chooseSkill(Pokemon target);
}
