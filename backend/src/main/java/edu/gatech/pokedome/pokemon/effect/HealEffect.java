package edu.gatech.pokedome.pokemon.effect;


import edu.gatech.pokedome.pokemon.Pokemon;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * An effect that heals the target Pokemon when it is used.
 */
@Getter
@Setter
@Builder
public class HealEffect implements Effect {

    private final int heal;

    public HealEffect(int heal) {
        this.heal = heal;
    }

    @Override
    public void apply(Pokemon attacker, Pokemon defender, StringBuffer results) {

        int maxHP = attacker.getBaseStat().getHitPoints();
        int currHp = attacker.getBattleStat().getHitPoints();

        // Cannot heal over base HP
        int delta = Math.min(heal, maxHP - currHp);
        attacker.getBattleStat().updateHitPoints(delta);

        results.append(attacker.getNickname()).append(" has healed ").append(delta).append(" damage, hp is ")
                .append(attacker.getBattleStat().getHitPoints()).append("\n");
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
        return;
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
