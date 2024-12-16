package edu.gatech.pokedome.pokemon.effect;

import edu.gatech.pokedome.helper.TypeKey;
import edu.gatech.pokedome.pokemon.Pokemon;
import edu.gatech.pokedome.pokemon.PokemonType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * An effect that deals damage to the target Pokemon.
 */
@Getter
@Setter
@Builder
public class DamageEffect implements Effect {

    private final int damage;
    private final PokemonType type;
    private static final HashMap<TypeKey, Double> typeMultiplier;

    static {
        typeMultiplier = new HashMap<>();
        typeMultiplier.put(new TypeKey(PokemonType.FIRE, PokemonType.FIRE), 0.5);
        typeMultiplier.put(new TypeKey(PokemonType.FIRE, PokemonType.WATER), 0.5);
        typeMultiplier.put(new TypeKey(PokemonType.FIRE, PokemonType.GRASS), 2.0);
        typeMultiplier.put(new TypeKey(PokemonType.FIRE, PokemonType.NORMAL), 1.0);

        typeMultiplier.put(new TypeKey(PokemonType.WATER, PokemonType.FIRE), 2.0);
        typeMultiplier.put(new TypeKey(PokemonType.WATER, PokemonType.WATER), 0.5);
        typeMultiplier.put(new TypeKey(PokemonType.WATER, PokemonType.GRASS), 0.5);
        typeMultiplier.put(new TypeKey(PokemonType.WATER, PokemonType.NORMAL), 1.0);

        typeMultiplier.put(new TypeKey(PokemonType.GRASS, PokemonType.FIRE), 0.5);
        typeMultiplier.put(new TypeKey(PokemonType.GRASS, PokemonType.WATER), 2.0);
        typeMultiplier.put(new TypeKey(PokemonType.GRASS, PokemonType.GRASS), 0.5);
        typeMultiplier.put(new TypeKey(PokemonType.GRASS, PokemonType.NORMAL), 1.0);

        typeMultiplier.put(new TypeKey(PokemonType.NORMAL, PokemonType.FIRE), 1.0);
        typeMultiplier.put(new TypeKey(PokemonType.NORMAL, PokemonType.WATER), 1.0);
        typeMultiplier.put(new TypeKey(PokemonType.NORMAL, PokemonType.GRASS), 1.0);
        typeMultiplier.put(new TypeKey(PokemonType.NORMAL, PokemonType.NORMAL), 1.0);
    }

    public DamageEffect(final int damage, PokemonType type) {
        this.damage = damage;
        this.type = type;
    }

    @Override
    public void apply(final Pokemon attacker, final Pokemon defender, final StringBuffer results) {
        // Use 1 as minimum rather than 0 in calc.
        final int defense = Math.max(defender.getBattleStat().getDefense(), 1);
        final int attack = Math.max(attacker.getBattleStat().getAttack(), 1);

        final double multiplier = getMultiplier(defender.getType());

        int calculatedDamage = (int) Math.round(damage * ((double) attack / defense) * multiplier);
        // DamageEffect only does damage, it cannot heal a Pokemon.
        if (calculatedDamage < 0) {
            calculatedDamage = 0;
        }
        defender.getBattleStat().updateHitPoints(-1 * calculatedDamage);

        results.append(attacker.getNickname()).append(" is attacking ").append(defender.getNickname()).append("\n");

        if (multiplier == 2.0) {
            results.append("It's super-effective!\n");
        }

        if (multiplier == 0.5) {
            results.append("It's not very effective.\n");
        }

        results.append(defender.getNickname()).append(" has received ").append(calculatedDamage).append(" damage, hp is ");
        results.append(defender.getBattleStat().getHitPoints()).append("\n");
    }

    @Override
    public void undo(final Pokemon target, StringBuffer results) {
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

    public double getMultiplier(final PokemonType type) {
        return typeMultiplier.get(new TypeKey(this.type, type));
    }
}
