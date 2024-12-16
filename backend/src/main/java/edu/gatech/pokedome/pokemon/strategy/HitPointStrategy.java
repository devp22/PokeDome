package edu.gatech.pokedome.pokemon.strategy;

import java.util.List;

import edu.gatech.pokedome.pokemon.skill.Skill;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import edu.gatech.pokedome.pokemon.Pokemon;

/**
 * A strategy that chooses a skill based on the target's hit points.
 */
@Builder
@Getter
@Setter
public class HitPointStrategy implements SkillStrategy {

    private final int aggression;
    private final int caution;

    public HitPointStrategy(int aggression, int caution) {
        this.aggression = aggression;
        this.caution = caution;
    }

    /**
     * Chooses a skill based on the target's hit points.
     * @param target the target Pokemon
     * @return the chosen skill
     */
    @Override
    public Skill chooseSkill(final Pokemon target) {
        final List<Skill> skillPool;

        if (this.isAttacking(target)) {
            skillPool = target.getOffensiveSkills();
        } else {
            skillPool = target.getDefensiveSkills();
        }

        final int skillIndex = target.getDecision(skillPool.size());
        return skillPool.get(skillIndex);
    }

    /**
     * Determines if the Pokemon should attack the target.
     * @param target the target Pokemon
     * @return true if the Pokemon should attack the target, false otherwise
     */
    private boolean isAttacking(final Pokemon target) {
        final double hitPointRatio =
            (double) target.getBattleStat().getHitPoints() / (double) target.getBaseStat().getHitPoints();

        // TODO: test/refine this calculation, maybe roll attack and defend 0-aggression and 0-caution
        final double aggressiveness = (double) aggression / caution;
        final int threshold = getThreshold(hitPointRatio * aggressiveness);

        final int attackRoll = target.getDecision(10);

        return attackRoll > threshold;
    }

    /**
     * Gets the threshold for attacking based on the target's hit points.
     * @param hitPointRatio the target's hit point ratio
     * @return the threshold for attacking
     */
    private int getThreshold(final double hitPointRatio) {
        int threshold = 0;
        if (hitPointRatio >= 0.7) {
            // 80% likelihood to attack
            threshold = 1;
        } else if (hitPointRatio >= 0.3) {
            // 50% likelihood to attack
            threshold = 4;
        } else {
            // 30% likelihood to attack
            threshold = 6;
        }

        return threshold;
    }
}
