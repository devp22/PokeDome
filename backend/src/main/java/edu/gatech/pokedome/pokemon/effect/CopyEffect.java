package edu.gatech.pokedome.pokemon.effect;

import edu.gatech.pokedome.pokemon.Pokemon;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * An effect that copies the last used skill of the target Pokemon.
 */
@Getter
@Setter
@Builder
public class CopyEffect implements Effect {

    public CopyEffect() {
    }

    @Override
    public void apply(final Pokemon attacker, final Pokemon defender, final StringBuffer results) {
        if (defender.getLastUsedSkill() == null ||
            defender.getLastUsedSkill().getEffects().stream().anyMatch(e -> e instanceof CopyEffect)) {
            results.append(attacker.getNickname()).append(" attempted to copy ").append(defender.getNickname())
                .append("'s last skill but failed and struggled instead\n");

            defender.getBattleStat().updateHitPoints(-10);
            results.append(defender.getNickname()).append(" has received ").append(10).append(" damage, hp is ");
            results.append(defender.getBattleStat().getHitPoints()).append("\n");
            return;
        }

        results.append(attacker.getNickname()).append(" copied ").append(defender.getNickname())
            .append("'s last skill\n");
        results.append(attacker.getNickname()).append(" is using ").append(defender.getLastUsedSkill().getName())
            .append("\n");
        defender.getLastUsedSkill().useSkill(attacker, defender, results);
    }

    @Override
    public void undo(final Pokemon target, final StringBuffer results) {
        return;
    }

    @Override
    public void prepareForTurn(final Pokemon pokemon, final StringBuffer results) {
        return;
    }

    @Override
    public void endTurn(final Pokemon pokemon, final StringBuffer results) {
        return;
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
