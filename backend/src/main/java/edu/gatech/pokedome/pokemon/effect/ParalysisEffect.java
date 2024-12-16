package edu.gatech.pokedome.pokemon.effect;


import edu.gatech.pokedome.pokemon.Pokemon;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * An effect that paralyzes the target Pokemon.
 * The target Pokemon only has a 50% chance of being unable to move at the start of its turn until the effect is over.
 */
@Getter
@Setter
@Builder
public class ParalysisEffect implements Effect {

    private int turnsActive;

    public ParalysisEffect(final int turnsActive) {
        this.turnsActive = turnsActive;
    }

    @Override
    public void apply(Pokemon attacker, Pokemon defender, StringBuffer results) {

        boolean alreadyParalysed = false;

        for (Effect effect : defender.getActiveEffects()) {
            if (effect instanceof ParalysisEffect paralysisEffect) {
                alreadyParalysed = true;
                int newTurnsActive = Math.max(paralysisEffect.getTurnsActive(), turnsActive);
                paralysisEffect.setTurnsActive(newTurnsActive);
                results.append(defender.getNickname()).append(" is already paralyzed and will now be paralyzed for ").append(newTurnsActive).append(" turns\n");
            }
        }

        if (!alreadyParalysed) {
            defender.addEffect(new ParalysisEffect(turnsActive));

            results.append(defender.getNickname()).append(" has been paralyzed for ").append(turnsActive).append(" turns\n");
        }
    }

    @Override
    public void undo(Pokemon target, StringBuffer results) {
        results.append(target.getNickname()).append(" is no longer paralyzed\n");
    }

    @Override
    public void prepareForTurn(Pokemon pokemon, StringBuffer results) {
        turnsActive -= 1;

        int paralyzed = pokemon.getDecision(2);

        if (paralyzed > 0) {
            pokemon.setAbleToMove(false);
            results.append(pokemon.getNickname()).append(" is paralyzed and can't move, ").append(turnsActive).append(" turns left\n");
        }
        else {
            results.append(pokemon.getNickname()).append(" is paralyzed but is able to move, ").append(turnsActive).append(" turns left\n");
        }
    }

    @Override
    public void endTurn(Pokemon pokemon, StringBuffer results) {
        return;
    }

    @Override
    public boolean isActive() {
        return turnsActive > 0;
    }
}
