package edu.gatech.pokedome.pokemon.effect;

import edu.gatech.pokedome.pokemon.Pokemon;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * An effect that deals damage to the target Pokemon over time.
 * The damage is applied when effect is used and also at the end of each turn until the effect is no longer active.
 */
@Getter
@Setter
@Builder
public class DamageOverTimeEffect implements Effect {

    private final int damage;
    private int turnsActive;

    public DamageOverTimeEffect(final int damage, final int turnsActive) {
        this.damage = damage;
        this.turnsActive = turnsActive;
    }

    @Override
    public void apply(Pokemon attacker, Pokemon defender, StringBuffer results) {
        defender.addEffect(new DamageOverTimeEffect(damage, turnsActive));

        results.append(defender.getNickname()).append(" will take ").append(damage).append(" damage for ")
                .append(turnsActive).append(" turns\n");
    }

    @Override
    public void undo(Pokemon target, StringBuffer results) {
        return;
    }

    @Override
    public void prepareForTurn(Pokemon pokemon, StringBuffer results) {
        return;
    }

    @Override
    public void endTurn(Pokemon pokemon, StringBuffer results) {
        pokemon.getBattleStat().updateHitPoints(-1 * damage);
        turnsActive -= 1;

        results.append(pokemon.getNickname()).append(" has taken ").append(damage).append(" damage, ").append(turnsActive)
                .append(" turns left, hp is ").append(pokemon.getBattleStat().getHitPoints()).append("\n");
    }

    @Override
    public boolean isActive() {
        return turnsActive > 0;
    }
}
