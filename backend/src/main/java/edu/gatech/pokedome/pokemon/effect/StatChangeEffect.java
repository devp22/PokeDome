package edu.gatech.pokedome.pokemon.effect;

import edu.gatech.pokedome.pokemon.Pokemon;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * An effect that changes the attack and/or defense stat of the target Pokemon for a number of turns.
 */
@Getter
@Setter
@Builder
public class StatChangeEffect implements Effect {

    private final int defenseChange;
    private final int attackChange;
    private int turnsActive;

    public StatChangeEffect(final int defenseChange, final int attackChange, final int turnsActive) {
        this.defenseChange = defenseChange;
        this.attackChange = attackChange;
        this.turnsActive = turnsActive;
    }

    @Override
    public void apply(final Pokemon attacker, final Pokemon defender, final StringBuffer results) {
        attacker.addEffect(new StatChangeEffect(defenseChange, attackChange, turnsActive));

        if (defenseChange != 0) {
            String modifierVerb = "increased";
            if (defenseChange < 0) {
                modifierVerb = "decreased";
            }

            attacker.getBattleStat().updateDefense(defenseChange);
            results.append(attacker.getNickname()).append(" has " + modifierVerb + " defense by ").append(defenseChange)
                .append(" for ")
                .append(turnsActive).append(" turns\n");
        }

        if (attackChange != 0) {
            String modifierVerb = "increased";
            if (attackChange < 0) {
                modifierVerb = "decreased";
            }

            attacker.getBattleStat().updateAttack(attackChange);
            results.append(attacker.getNickname()).append(" has " + modifierVerb + " attack by ").append(attackChange)
                .append(" for ")
                .append(turnsActive).append(" turns\n");
        }
    }

    @Override
    public void undo(final Pokemon target, final StringBuffer results) {
        if (defenseChange != 0) {
            target.getBattleStat().updateDefense(-defenseChange);
            results.append(target.getNickname()).append(" has had their defense change of ").append(defenseChange)
                .append(" wear off\n");
        }

        if (attackChange != 0) {
            target.getBattleStat().updateAttack(-attackChange);
            results.append(target.getNickname()).append(" has had their attack change of ").append(attackChange)
                .append(" wear off\n");
        }
    }

    @Override
    public void prepareForTurn(final Pokemon pokemon, final StringBuffer results) {
        turnsActive -= 1;

        if (defenseChange != 0) {
            String modifierVerb = "increased";
            if (defenseChange < 0) {
                modifierVerb = "decreased";
            }

            results.append(pokemon.getNickname()).append(" has their defense " + modifierVerb + " by ")
                .append(defenseChange).append(" for " + turnsActive + " more turns.\n");
        }

        if (attackChange != 0) {
            String modifierVerb = "increased";
            if (attackChange < 0) {
                modifierVerb = "decreased";
            }

            results.append(pokemon.getNickname()).append(" has their attack " + modifierVerb + " by ")
                .append(attackChange)
                .append(" for " + turnsActive + " more turns.\n");
        }
    }

    @Override
    public void endTurn(final Pokemon pokemon, final StringBuffer results) {
        return;
    }

    @Override
    public boolean isActive() {
        return turnsActive > 0;
    }
}
