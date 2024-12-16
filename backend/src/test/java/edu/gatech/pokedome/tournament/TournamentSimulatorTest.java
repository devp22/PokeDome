package edu.gatech.pokedome.tournament;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.gatech.pokedome.battle.Battle;
import edu.gatech.pokedome.pokemon.Pokemon;
import edu.gatech.pokedome.pokemon.PokemonType;
import edu.gatech.pokedome.pokemon.effect.DamageEffect;
import edu.gatech.pokedome.pokemon.effect.StatChangeEffect;
import edu.gatech.pokedome.pokemon.skill.Skill;
import edu.gatech.pokedome.pokemon.statGroup.StatGroup;
import edu.gatech.pokedome.pokemon.strategy.HitPointStrategy;

@ExtendWith(MockitoExtension.class)
public class TournamentSimulatorTest {
    @InjectMocks
    private TournamentSimulator tournamentSimulator;

    private final HitPointStrategy TEST_HIT_POINT_STRATEGY = new HitPointStrategy(1, 1);

    private final List<Pokemon> TEST_POKEMONS = List.of(
        new Pokemon("Pikachu", PokemonType.GRASS,
            TEST_HIT_POINT_STRATEGY, getStatGroup(20),
            List.of(getOffensiveSkill("Tackle", 5, PokemonType.NORMAL)),
            List.of(getDefensiveSkill("Block", 5))),
        new Pokemon("Bulbasaur", PokemonType.FIRE,
            TEST_HIT_POINT_STRATEGY, getStatGroup(20),
            List.of(getOffensiveSkill("Tackle", 5, PokemonType.NORMAL)),
            List.of(getDefensiveSkill("Block", 5))),
        new Pokemon("Charizard", PokemonType.GRASS,
            TEST_HIT_POINT_STRATEGY, getStatGroup(20),
            List.of(getOffensiveSkill("Tackle", 5, PokemonType.NORMAL)),
            List.of(getDefensiveSkill("Block", 5))),
        new Pokemon("Squirtle", PokemonType.WATER,
            TEST_HIT_POINT_STRATEGY, getStatGroup(25),
            List.of(getOffensiveSkill("Tackle", 6, PokemonType.NORMAL)),
            List.of(getDefensiveSkill("Block", 5))));

    @Test
    public void testSimulateTournament_whenSingleElimination() {
        final List<Battle> battles = tournamentSimulator.simulateTournament(
            TEST_POKEMONS, 8, TournamentType.SINGLE_ELIMINATION, new StringBuffer());
        assertThat(battles.size(), equalTo(3));
        assertThat(battles.get(0).getFirstPokemon().getNickname(), equalTo("Pikachu"));
        assertThat(battles.get(0).getSecondPokemon().getNickname(), equalTo("Bulbasaur"));
        assertThat(battles.get(1).getFirstPokemon().getNickname(), equalTo("Charizard"));
        assertThat(battles.get(1).getSecondPokemon().getNickname(), equalTo("Squirtle"));
        assertThat(battles.get(2).getFirstPokemon().getNickname(), equalTo("Pikachu"));
        assertThat(battles.get(2).getSecondPokemon().getNickname(), equalTo("Squirtle"));
    }

    @Test
    public void testSimulateTournament_whenSingleEliminationOddNumber() {
        final List<Pokemon> testPokemons = new ArrayList<>(TEST_POKEMONS);
        testPokemons.add(new Pokemon("Cyndaquil", PokemonType.FIRE,
            TEST_HIT_POINT_STRATEGY, getStatGroup(20),
            List.of(getOffensiveSkill("Tackle", 5, PokemonType.NORMAL)),
            List.of(getDefensiveSkill("Block", 5))));
        final List<Battle> battles = tournamentSimulator.simulateTournament(testPokemons, 8,
            TournamentType.SINGLE_ELIMINATION, new StringBuffer());
        assertThat(battles.size(), equalTo(4));
        assertThat(battles.get(0).getFirstPokemon().getNickname(), equalTo("Pikachu"));
        assertThat(battles.get(0).getSecondPokemon().getNickname(), equalTo("Bulbasaur"));
        assertThat(battles.get(1).getFirstPokemon().getNickname(), equalTo("Charizard"));
        assertThat(battles.get(1).getSecondPokemon().getNickname(), equalTo("Squirtle"));
        assertThat(battles.get(2).getFirstPokemon().getNickname(), equalTo("Pikachu"));
        assertThat(battles.get(2).getSecondPokemon().getNickname(), equalTo("Squirtle"));
        assertThat(battles.get(3).getFirstPokemon().getNickname(), equalTo("Squirtle"));
        assertThat(battles.get(3).getSecondPokemon().getNickname(), equalTo("Cyndaquil"));
    }

    @Test
    public void testSimulateTournament_whenDoubleElimination() {
        final List<Battle> battles = tournamentSimulator.simulateTournament(
            TEST_POKEMONS, 8, TournamentType.DOUBLE_ELIMINATION, new StringBuffer());
        assertThat(battles.size(), equalTo(6));
        // Set 1, winners rounds
        assertThat(battles.get(0).getFirstPokemon().getNickname(), equalTo("Pikachu"));
        assertThat(battles.get(0).getSecondPokemon().getNickname(), equalTo("Bulbasaur"));
        assertThat(battles.get(1).getFirstPokemon().getNickname(), equalTo("Charizard"));
        assertThat(battles.get(1).getSecondPokemon().getNickname(), equalTo("Squirtle"));
        // Set 1, losers rounds
        assertThat(battles.get(2).getFirstPokemon().getNickname(), equalTo("Bulbasaur"));
        assertThat(battles.get(2).getSecondPokemon().getNickname(), equalTo("Charizard"));
        // Set 2, winners rounds
        assertThat(battles.get(3).getFirstPokemon().getNickname(), equalTo("Pikachu"));
        assertThat(battles.get(3).getSecondPokemon().getNickname(), equalTo("Squirtle"));
        // Set 2, losers rounds
        assertThat(battles.get(4).getFirstPokemon().getNickname(), equalTo("Bulbasaur"));
        assertThat(battles.get(4).getSecondPokemon().getNickname(), equalTo("Pikachu"));
        // Final battle
        assertThat(battles.get(5).getFirstPokemon().getNickname(), equalTo("Squirtle"));
        assertThat(battles.get(5).getSecondPokemon().getNickname(), equalTo("Bulbasaur"));
    }

    @Test
    public void testSimulateTournament_whenDoubleEliminationOddNumber() {
        final List<Pokemon> testPokemons = new ArrayList<>(TEST_POKEMONS);
        testPokemons.add(new Pokemon("Cyndaquil", PokemonType.FIRE,
            TEST_HIT_POINT_STRATEGY, getStatGroup(20),
            List.of(getOffensiveSkill("Tackle", 5, PokemonType.NORMAL)),
            List.of(getDefensiveSkill("Block", 5))));
        final List<Battle> battles = tournamentSimulator.simulateTournament(testPokemons, 8,
            TournamentType.DOUBLE_ELIMINATION, new StringBuffer());
        assertThat(battles.size(), equalTo(8));
        // Set 1, winners rounds
        assertThat(battles.get(0).getFirstPokemon().getNickname(), equalTo("Pikachu"));
        assertThat(battles.get(0).getSecondPokemon().getNickname(), equalTo("Bulbasaur"));
        assertThat(battles.get(1).getFirstPokemon().getNickname(), equalTo("Charizard"));
        assertThat(battles.get(1).getSecondPokemon().getNickname(), equalTo("Squirtle"));
        // Set 1, losers rounds
        assertThat(battles.get(2).getFirstPokemon().getNickname(), equalTo("Bulbasaur"));
        assertThat(battles.get(2).getSecondPokemon().getNickname(), equalTo("Charizard"));
        // Set 2, winners rounds
        assertThat(battles.get(3).getFirstPokemon().getNickname(), equalTo("Pikachu"));
        assertThat(battles.get(3).getSecondPokemon().getNickname(), equalTo("Squirtle"));
        // Set 2, losers rounds
        assertThat(battles.get(4).getFirstPokemon().getNickname(), equalTo("Bulbasaur"));
        assertThat(battles.get(4).getSecondPokemon().getNickname(), equalTo("Pikachu"));
        // Set 3, winners rounds
        assertThat(battles.get(5).getFirstPokemon().getNickname(), equalTo("Squirtle"));
        assertThat(battles.get(5).getSecondPokemon().getNickname(), equalTo("Cyndaquil"));
        // Set 3, losers rounds
        assertThat(battles.get(6).getFirstPokemon().getNickname(), equalTo("Bulbasaur"));
        assertThat(battles.get(6).getSecondPokemon().getNickname(), equalTo("Cyndaquil"));
        // Final battle
        assertThat(battles.get(7).getFirstPokemon().getNickname(), equalTo("Squirtle"));
        assertThat(battles.get(7).getSecondPokemon().getNickname(), equalTo("Bulbasaur"));
    }

    @Test
    public void testSimulateTournament_whenRoundRobin() {
        final List<Battle> battles = tournamentSimulator.simulateTournament(
            TEST_POKEMONS, 8, TournamentType.ROUND_ROBIN, new StringBuffer());
        assertThat(battles.size(), equalTo(6));
        assertThat(battles.get(0).getFirstPokemon().getNickname(), equalTo("Pikachu"));
        assertThat(battles.get(0).getSecondPokemon().getNickname(), equalTo("Bulbasaur"));
        assertThat(battles.get(1).getFirstPokemon().getNickname(), equalTo("Pikachu"));
        assertThat(battles.get(1).getSecondPokemon().getNickname(), equalTo("Charizard"));
        assertThat(battles.get(2).getFirstPokemon().getNickname(), equalTo("Pikachu"));
        assertThat(battles.get(2).getSecondPokemon().getNickname(), equalTo("Squirtle"));
        assertThat(battles.get(3).getFirstPokemon().getNickname(), equalTo("Bulbasaur"));
        assertThat(battles.get(3).getSecondPokemon().getNickname(), equalTo("Charizard"));
        assertThat(battles.get(4).getFirstPokemon().getNickname(), equalTo("Bulbasaur"));
        assertThat(battles.get(4).getSecondPokemon().getNickname(), equalTo("Squirtle"));
        assertThat(battles.get(5).getFirstPokemon().getNickname(), equalTo("Charizard"));
        assertThat(battles.get(5).getSecondPokemon().getNickname(), equalTo("Squirtle"));
    }

    private Skill getOffensiveSkill(final String name, final int damage, PokemonType type) {
        return Skill.builder().name(name)
            .effects(List.of(DamageEffect.builder().damage(damage).type(type).build()))
            .build();
    }

    private Skill getDefensiveSkill(final String name, final int defense) {
        return Skill.builder()
            .name(name)
            .effects(List.of(StatChangeEffect.builder().defenseChange(defense).turnsActive(1).build()))
            .build();
    }

    private StatGroup getStatGroup(final int hp) {
        return StatGroup.builder().hitPoints(hp).attack(0).defense(0).build();
    }
}
