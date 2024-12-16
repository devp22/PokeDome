package edu.gatech.pokedome.pokemon.statGroup;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * A group of stats that a Pokemon has.
 */
@Builder
@Getter
@ToString
public class StatGroup {
    private int hitPoints;
    private int attack;
    private int defense;

    public StatGroup(final int hitPoints, final int attack, final int defense) {
        this.hitPoints = hitPoints;
        this.attack = attack;
        this.defense = defense;
    }

    public void updateGroup(final StatGroup statsToCopy) {
        this.hitPoints = statsToCopy.getHitPoints();
        this.attack = statsToCopy.getAttack();
        this.defense = statsToCopy.getDefense();
    }

    public void updateHitPoints(final int delta) {
        this.hitPoints = addDelta(this.hitPoints, delta);
    }

    public void updateAttack(final int delta) {
        this.attack = addDelta(this.attack, delta);
    }

    public void updateDefense(final int delta) {
        this.defense = addDelta(this.defense, delta);
    }

    private int addDelta(int stat, final int delta) {
        stat += delta;
        return Math.max(stat, 0);
    }
}
