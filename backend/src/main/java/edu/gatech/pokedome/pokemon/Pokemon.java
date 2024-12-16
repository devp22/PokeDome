package edu.gatech.pokedome.pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.lang.Nullable;

import edu.gatech.pokedome.pokemon.effect.Effect;
import edu.gatech.pokedome.pokemon.skill.Skill;
import edu.gatech.pokedome.pokemon.statGroup.StatGroup;
import edu.gatech.pokedome.pokemon.strategy.SkillStrategy;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A Pokemon that can battle other Pokemon.
 */
@Getter
@Setter
@ToString
public class Pokemon {
    // Functional parameters
    private String nickname;
    private PokemonType type;
    private Random random;
    private SkillStrategy skillStrategy;
    private StatGroup baseStat;
    private StatGroup battleStat;
    private List<Effect> activeEffects;
    private List<Skill> offensiveSkills;
    private List<Skill> defensiveSkills;
    private Skill lastUsedSkill;
    private boolean ableToMove;

    public Pokemon(final String name, final PokemonType type, final SkillStrategy skillStrategy, final StatGroup stats,
        final List<Skill> offensiveSkills, final List<Skill> defensiveSkills) {
        this.nickname = name;
        this.type = type;
        this.skillStrategy = skillStrategy;
        this.baseStat = stats;
        this.offensiveSkills = offensiveSkills;
        this.defensiveSkills = defensiveSkills;

        this.random = new Random();
        this.battleStat = new StatGroup(-1, -1, -1); // Undefined until preparing for battle.
        this.activeEffects = new ArrayList<>();
        this.lastUsedSkill = null;
        this.ableToMove = true;
    }

    /**
     * Gets a random number between 0 and the given bound.
     * @param bound the upper bound of the random number
     * @return the random number
     */
    public int getDecision(final int bound) {
        return this.random.nextInt(bound);
    }

    /**
     * Prepares the Pokemon for battle by resetting its stats and effects.
     * @param seed the seed to use for the random number generator, or null to not use a seed
     */
    public void prepareForBattle(@Nullable final Integer seed) {
        if (seed != null) {
            this.random.setSeed(seed);
        }
        this.battleStat.updateGroup(baseStat);
        this.activeEffects.clear();
        this.ableToMove = true;
        this.lastUsedSkill = null;
    }

    /**
     * Prepares the Pokemon for its turn by applying effects and removing inactive effects.
     * @param results the StringBuffer to append the results of the turn to
     */
    public void prepareForTurn(final StringBuffer results) {
        // Must put removed effects in separate list to prevent
        //   concurrent modification of the activeEffects while we
        //   are iterating over them.
        this.ableToMove = true;
        this.lastUsedSkill = null;
        final List<Effect> removed = new ArrayList<Effect>();
        for (final Effect effect : this.activeEffects) {
            effect.prepareForTurn(this, results);
            if (!effect.isActive()) {
                effect.undo(this, results);
                removed.add(effect);
            }
        }
        for (final Effect effect : removed) {
            activeEffects.remove(effect);
        }
    }

    /**
     * Ends the Pokemon's turn by applying effects and removing inactive effects.
     * @param results the StringBuffer to append the results of the turn to
     */
    public void endTurn(final StringBuffer results) {
        for (final Effect effect : activeEffects) {
            effect.endTurn(this, results);
        }
    }

    /**
     * Chooses a skill to use in battle based on the Pokemon's strategy.
     * @return the skill to use
     */
    public Skill chooseSkill() {
        return skillStrategy.chooseSkill(this);
    }

    /**
     * Check if the Pokemon has fainted.
     * @return true if the Pokemon has fainted, false otherwise
     */
    public boolean isFainted() {
        return this.battleStat.getHitPoints() <= 0;
    }

    /**
     * Adds an effect to the Pokemon.
     * @param effect the effect to add
     */
    public void addEffect(final Effect effect) {
        this.activeEffects.add(effect);
    }
}
