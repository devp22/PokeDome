package edu.gatech.pokedome.pokemon.skill;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.pokedome.pokemon.Pokemon;
import edu.gatech.pokedome.pokemon.effect.Effect;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A skill that a Pokemon can use in battle.
 */
@Getter
@Setter
@Builder
@ToString
public class Skill {
    private String name;
    private List<Effect> effects;

    public Skill(final String name, final List<Effect> effects) {
        this.name = name;
        this.effects = effects;
    }

    public Skill(final String name) {
        this.name = name;
        this.effects = new ArrayList<Effect>();
    }

    /**
     * Uses the skill on the target Pokemon.
     *
     * @param attacker the Pokemon using the skill
     * @param defender the Pokemon being targeted
     * @param results       the StringBuffer to append the results of the skill to
     */
    public void useSkill(final Pokemon attacker, final Pokemon defender, final StringBuffer results) {
        for (final Effect effect : effects) {
            effect.apply(attacker, defender, results);
        }
    }
}
