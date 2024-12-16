package edu.gatech.pokedome.pokemon.effect;

import edu.gatech.pokedome.pokemon.Pokemon;

/**
 * Effect interface for Pokemon effects.
 */
public interface Effect {
    /**
     * Applies the effect to the attacker and defender. Happens skill with the effect is used.
     *
     * @param attacker Pokemon using the skill
     * @param defender Pokemon targeted by the skill
     * @param sb StringBuffer to append messages to
     */
    void apply(Pokemon attacker, Pokemon defender, StringBuffer results);

    /**
     * Undoes the effect on the target. Happens when the effect is removed.
     *
     * @param target Pokemon to remove the effect from
     * @param sb StringBuffer to append messages to
     */
    void undo(Pokemon target, StringBuffer results);

    /**
     * Prepares the effect for the turn. Happens at the start of the turn.
     * Typically used to decrement the duration of the effect.
     *
     * @param pokemon Pokemon to prepare the effect for
     * @param sb StringBuffer to append messages to
     */
    void prepareForTurn(Pokemon pokemon, StringBuffer results);

    /**
     * Ends the effect for the turn. Happens at the end of the turn.
     *
     * @param pokemon Pokemon the effect is ending for
     * @param sb StringBuffer to append messages to
     */
    void endTurn(Pokemon pokemon, StringBuffer results);

    /**
     * Checks if the effect is active.
     *
     * @return true if the effect is active, false otherwise
     */
    boolean isActive();
}
