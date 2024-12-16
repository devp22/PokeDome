package edu.gatech.pokedome.battle;

import org.springframework.lang.Nullable;

import edu.gatech.pokedome.pokemon.Pokemon;
import edu.gatech.pokedome.pokemon.skill.Skill;
import lombok.Getter;

@Getter
public class Battle {
    // Null seed value means there is no seed
    private final Integer seed;
    private final Pokemon firstPokemon; // TODO: renamed this field, update in diagrams
    private final Pokemon secondPokemon; // TODO: renamed this field, update in diagrams
    private Pokemon winner;

    public Battle(@Nullable final Integer seed, final Pokemon firstPokemon, final Pokemon secondPokemon) {
        this.seed = seed;
        this.firstPokemon = firstPokemon;
        this.secondPokemon = secondPokemon;
    }

    public Pokemon getLoser() {
        if (winner == null) {
            return null;
        }
        return (winner == firstPokemon) ? secondPokemon : firstPokemon;
    }

    public void startBattle(final StringBuffer results) {
        // Verify this is a valid battle.
        if (firstPokemon == null) {
            System.out.println("Pokemon X is null, battle terminating");
            return;
        }
        if (secondPokemon == null) {
            System.out.println("Pokemon Y is null, battle terminating");
            return;
        }

        results.append("Starting battle between ").append(firstPokemon.getNickname()).append(" and ")
            .append(secondPokemon.getNickname()).append("\n");

        // Prepare for the start of the battle.
        firstPokemon.prepareForBattle(seed);

        // Null seed indicates no seed was set. 
        // Don't try to add 1 to null.
        if (seed != null) {
            secondPokemon.prepareForBattle(seed + 1);
        } else {
            secondPokemon.prepareForBattle(seed);
        }

        // pX should go first, but switch the order on initialization because
        //   the while loop will swap them back to the right order.
        Pokemon attacker = secondPokemon;
        Pokemon defender = firstPokemon;

        // Used temporarily to switch the attacker and defender pokemon.
        Pokemon temp;
        Skill chosenSkill;

        // Main battle loop.
        // Check both for fainted in case of self-damaging moves or lingering effects.
        while (!defender.isFainted() && !attacker.isFainted()) {

            // Switch attacker and defender.
            // Do this first and not last so that when the battle ends,
            //   the attacker/defender will remain the pokemon they
            //   were on the last turn.
            temp = attacker;
            attacker = defender;
            defender = temp;

            attacker.prepareForTurn(results);

            if (attacker.isAbleToMove()) {
                chosenSkill = attacker.chooseSkill();

                results.append(attacker.getNickname()).append(" has chosen to use skill ").append(chosenSkill.getName())
                    .append("\n");

                chosenSkill.useSkill(attacker, defender, results);
                attacker.setLastUsedSkill(chosenSkill);
            }

            attacker.endTurn(results);
        }

        // Assign the winner
        if (defender.isFainted()) {
            winner = attacker;
        } else {
            winner = defender;
        }

        results.append(winner.getNickname()).append(" won the battle!\n");
        results.append("--------------------\n");
    }
}
