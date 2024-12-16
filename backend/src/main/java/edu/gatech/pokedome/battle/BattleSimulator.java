package edu.gatech.pokedome.battle;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.pokedome.pokemon.Pokemon;
import lombok.Getter;
import lombok.Setter;

/**
 * A class that simulates Pokemon battles.
 */
@Getter
@Setter
public class BattleSimulator {
    // Null seed value means there is no seed
    private Integer seed;
    private List<Battle> battleHistory;

    public BattleSimulator(final Integer seed) {
        this.seed = seed;
        this.battleHistory = new ArrayList<Battle>();
    }

    public void removeSeed() {
        this.seed = null;
    }

    /**
     * Starts a battle between two Pokemons.
     *
     * @param firstPokemon Pokemon who will attack first
     * @param secondPokemon Pokemon who will attack second
     * @param results StringBuffer to append the battle information
     */
    public void battle(final Pokemon firstPokemon, final Pokemon secondPokemon, final StringBuffer results) {
        final Battle battle = new Battle(this.seed, firstPokemon, secondPokemon);
        this.battleHistory.add(battle);
        battle.startBattle(results);
    }
}
