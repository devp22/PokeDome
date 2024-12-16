package edu.gatech.pokedome.tournament;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.lang.Nullable;

import edu.gatech.pokedome.battle.Battle;
import edu.gatech.pokedome.pokemon.Pokemon;
import lombok.NoArgsConstructor;

/**
 * Simulates a tournament with a list of Pokemon.
 */
@NoArgsConstructor
public class TournamentSimulator {
    /**
     * Simulates a tournament with the given list of Pokemon and tournament type.
     * @param pokemons List of Pokemon to simulate the tournament with
     * @param seed    Optional seed for the random number generator
     * @param tournamentType Type of tournament to simulate
     * @param results     StringBuffer to append the tournament results to
     * @return List of Battle objects representing the battles that took place in the tournament
     */
    public List<Battle> simulateTournament(final List<Pokemon> pokemons, @Nullable final Integer seed,
        final TournamentType tournamentType, final StringBuffer results) {
        final List<Battle> battleHistory = new ArrayList<>();
        switch (tournamentType) {
        case SINGLE_ELIMINATION:
            printTournamentStartMessage(pokemons, results);
            simulateSingleEliminationTournament(pokemons, results, seed, battleHistory);
            break;
        case DOUBLE_ELIMINATION:
            printTournamentStartMessage(pokemons, results);
            simulateDoubleEliminationTournament(pokemons, results, seed, battleHistory);
            break;
        case ROUND_ROBIN:
            printTournamentStartMessage(pokemons, results);
            simulateRoundRobinTournament(pokemons, results, seed, battleHistory);
            break;
        }
        return battleHistory;
    }

    private void printTournamentStartMessage(final List<Pokemon> pokemons, final StringBuffer results) {
        results.append("tournament, ").append(pokemons.getFirst().getNickname());
        for (int i = 1; i < pokemons.size(); i++) {
            results.append(", ").append(pokemons.get(i).getNickname());
        }
        results.append("\n");
    }

    /**
     * Simulates a single elimination tournament with the given list of Pokemon. If there is an odd number of Pokemon,
     * the last one automatically advances to the next round.
     *
     * @param pokemons List of Pokemon to simulate the tournament with
     * @param results StringBuffer to append the tournament results to
     * @param seed     Optional seed for the random number generator
     * @param battleHistory List to add the battles to
     */
    private void simulateSingleEliminationTournament(final List<Pokemon> pokemons, final StringBuffer results,
        final Integer seed, final List<Battle> battleHistory) {

        List<Pokemon> currentRound = new ArrayList<>(pokemons);
        final List<Pokemon> nextRound = new ArrayList<>();
        int roundCount = 1;

        while (currentRound.size() > 1) {
            for (int i = 0; i < currentRound.size(); i += 2) {
                if (i + 1 < currentRound.size()) {
                    results.append("Starting tournament round ").append(roundCount).append(" with ")
                        .append(currentRound.get(i).getNickname()).append(" and ")
                        .append(currentRound.get(i + 1).getNickname()).append("!\n");

                    final Battle battle = new Battle(seed, currentRound.get(i), currentRound.get(i + 1));
                    battle.startBattle(results);
                    battleHistory.add(battle);

                    nextRound.add(battle.getWinner());

                    results.append(battle.getWinner().getNickname()).append(" has won round ").append(roundCount)
                        .append("\n");
                } else {
                    // If there's an odd number of Pokemon, the last one automatically advances
                    nextRound.add(currentRound.get(i));
                }
                roundCount++;
            }
            currentRound = new ArrayList<>(nextRound);
            nextRound.clear();
        }

        if (!currentRound.isEmpty()) {
            results.append(currentRound.getFirst().getNickname()).append(" has won the tournament!\n");
        }
    }

    /**
     * Simulates a double elimination tournament with the given list of Pokemon.
     * If there is an odd number of Pokemon, the last one automatically advances to the next round.
     *
     * @param pokemons List of Pokemon to simulate the tournament with
     * @param results      StringBuffer to append the tournament results to
     * @param seed     Optional seed for the random number generator
     * @param battleHistory List to add the battles to
     */
    private void simulateDoubleEliminationTournament(final List<Pokemon> pokemons, final StringBuffer results,
        final Integer seed, final List<Battle> battleHistory) {

        List<Pokemon> currentWinnersRound = new ArrayList<>(pokemons);
        final List<Pokemon> nextWinnersRound = new ArrayList<>();
        List<Pokemon> currentLosersRound = new ArrayList<>();
        final List<Pokemon> nextLosersRound = new ArrayList<>();
        int winnersRoundCount = 1;
        int losersRoundCount = 1;
        int setCount = 1;

        while (currentWinnersRound.size() > 1 || currentLosersRound.size() > 1) {
            results.append("Starting tournament set ").append(setCount).append(".\n");
            for (int i = 0; i < currentWinnersRound.size(); i += 2) {
                if (i + 1 < currentWinnersRound.size()) {
                    results.append("Starting tournament winners bracket round ").append(winnersRoundCount)
                        .append(" with ")
                        .append(currentWinnersRound.get(i).getNickname()).append(" and ")
                        .append(currentWinnersRound.get(i + 1).getNickname()).append("!\n");

                    final Battle battle = new Battle(seed, currentWinnersRound.get(i), currentWinnersRound.get(i + 1));
                    battle.startBattle(results);
                    battleHistory.add(battle);

                    nextWinnersRound.add(battle.getWinner());
                    currentLosersRound.add(battle.getLoser());

                    results.append(battle.getWinner().getNickname()).append(" has won winners round ")
                        .append(winnersRoundCount).append(". ")
                        .append(battle.getLoser().getNickname()).append(" falls into the losers bracket.")
                        .append("\n");
                    winnersRoundCount++;
                } else {
                    // If there's an odd number of Pokemon, the last one automatically advances
                    nextWinnersRound.add(currentWinnersRound.get(i));
                }
            }
            currentWinnersRound = new ArrayList<>(nextWinnersRound);
            nextWinnersRound.clear();

            for (int i = 0; i < currentLosersRound.size(); i += 2) {
                if (i + 1 < currentLosersRound.size()) {
                    results.append("Starting tournament losers bracket round ").append(losersRoundCount)
                        .append(" with ")
                        .append(currentLosersRound.get(i).getNickname()).append(" and ")
                        .append(currentLosersRound.get(i + 1).getNickname()).append("!\n");

                    final Battle battle = new Battle(seed, currentLosersRound.get(i), currentLosersRound.get(i + 1));
                    battle.startBattle(results);
                    battleHistory.add(battle);

                    nextLosersRound.add(battle.getWinner());

                    results.append(battle.getWinner().getNickname()).append(" has won losers round ")
                        .append(losersRoundCount).append(". ")
                        .append(battle.getLoser().getNickname()).append(" is eliminated from the tournament.")
                        .append("\n");
                    losersRoundCount++;
                } else {
                    // If there's an odd number of Pokemon, the last one automatically advances
                    nextLosersRound.add(currentLosersRound.get(i));
                }
            }
            currentLosersRound = new ArrayList<>(nextLosersRound);
            nextLosersRound.clear();

            setCount++;
        }

        results.append("Starting the final battle of the tournament, winners bracket round ").append(winnersRoundCount)
            .append(" between ")
            .append(currentWinnersRound.get(0).getNickname()).append(" and ")
            .append(currentLosersRound.get(0).getNickname()).append("!\n");

        final Battle battle = new Battle(seed, currentWinnersRound.get(0), currentLosersRound.get(0));
        battle.startBattle(results);
        battleHistory.add(battle);

        results.append(battle.getWinner().getNickname()).append(" has won the tournament!\n");
    }

    /**
     * Simulates a round robin tournament with the given list of Pokemon.
     * Each Pokemon battles every other Pokemon once and the winner is the Pokemon with the most wins.
     * If there is a tie, all Pokemon with the most wins are declared winners.
     *
     * @param pokemons List of Pokemon to simulate the tournament with
     * @param results StringBuffer to append the tournament results to
     * @param seed     Optional seed for the random number generator
     * @param battleHistory List to add the battles to
     */
    private void simulateRoundRobinTournament(final List<Pokemon> pokemons, final StringBuffer results,
        final Integer seed,
        final List<Battle> battleHistory) {

        final Map<Pokemon, Integer> winCount = new HashMap<>();
        for (final Pokemon pokemon : pokemons) {
            winCount.put(pokemon, 0);
        }

        // Each Pokemon battles every other Pokemon once
        for (int i = 0; i < pokemons.size(); i++) {
            for (int j = i + 1; j < pokemons.size(); j++) {
                final Pokemon firstPokemon = pokemons.get(i);
                final Pokemon secondPokemon = pokemons.get(j);
                results.append("Starting round robin battle between ").append(firstPokemon.getNickname())
                    .append(" and ")
                    .append(secondPokemon.getNickname()).append("\n");

                final Battle battle = new Battle(seed, firstPokemon, secondPokemon);
                battle.startBattle(results);
                battleHistory.add(battle);

                final Pokemon winner = battle.getWinner();

                // Update win and lose counts
                winCount.put(winner, winCount.get(winner) + 1);
            }
        }

        // Determine the Pokemon with the highest win count
        final List<Pokemon> topPokemons = new ArrayList<>();
        int maxWins = 0;
        for (final Map.Entry<Pokemon, Integer> entry : winCount.entrySet()) {
            if (entry.getValue() > maxWins) {
                maxWins = entry.getValue();
                topPokemons.clear();
                topPokemons.add(entry.getKey());
            } else if (entry.getValue() == maxWins) {
                topPokemons.add(entry.getKey());
            }
        }

        if (topPokemons.size() > 1) {
            results.append("There is a tie! There winners of the round-robin tournament are: ")
                .append(topPokemons.get(0).getNickname()).append(", ");
            for (int i = 1; i < topPokemons.size(); i++) {
                results.append(topPokemons.get(i).getNickname()).append(", ");
            }
            results.append("\n");
        } else {
            results.append("The winner of the round-robin tournament is ").append(topPokemons.get(0).getNickname())
                .append(" with ")
                .append(maxWins).append(" wins!\n");
        }
    }
}
