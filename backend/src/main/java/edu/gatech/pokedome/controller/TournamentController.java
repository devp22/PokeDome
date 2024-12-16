package edu.gatech.pokedome.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.gatech.pokedome.battle.Battle;
import edu.gatech.pokedome.controller.dto.PokemonDTO;
import edu.gatech.pokedome.controller.dto.converter.PokemonConverter;
import edu.gatech.pokedome.pokemon.Pokemon;
import edu.gatech.pokedome.tournament.TournamentSimulator;
import edu.gatech.pokedome.tournament.TournamentType;
import lombok.RequiredArgsConstructor;

/**
 * Controller for handling requests to start a tournament.
 */
@RestController
@RequiredArgsConstructor
public class TournamentController {
    private final PokemonConverter pokemonConverter;
    private final TournamentSimulator tournamentSimulator;

    /**
     * Handles POST requests to /tournament. The request body should contain a list of PokemonDTOs, a seed, and a tournament type.
     * The method will simulate a tournament with the Pokemons.
     *
     * @param request TournamentRequest containing the list of PokemonDTOs, seed, and tournament type
     * @return tournament log
     */
    @RequestMapping(
        value = "/tournament",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> battle(@RequestBody final TournamentRequest request) {
        final List<PokemonDTO> pokemons = request.getPokemons();
        if (pokemons.size() < 4) {
            return ResponseEntity.badRequest().body("At least four pokemons are required to start a tournament");
        }

        final List<Pokemon> battlePokemons;
        try {
            battlePokemons = pokemons.stream()
                .map(pokemonConverter::convertToEntity)
                .toList();
        } catch (final Exception e) {
            System.out.println("Issues converting PokemonDTO. Error: " + e.toString());
            return ResponseEntity.badRequest().body("Invalid request");
        }

        for (final Pokemon p : battlePokemons) {
            if (!isValidPokemon(p)) {
                return ResponseEntity.ok(
                    "Invalid request: " + p.getNickname()
                        + " does not have at least 1 defensive and 1 offensive Skill");
            }
        }

        final StringBuffer sb = new StringBuffer();
        sb.append("Welcome to the PokeDome!\n");
        final List<Battle> battleHistory = tournamentSimulator.simulateTournament(battlePokemons, request.getSeed(),
            TournamentType.fromString(request.getTournamentType()), sb);
        System.out.println(sb.toString());
        return ResponseEntity.ok(sb.toString());
    }

    /**
     * Checks if the given Pokemon is valid. Valid Pokemon must have at least 1 offensive and 1 defensive Skill.
     *
     * @param p Pokemon
     * @return true if pokemon is valid
     */
    private boolean isValidPokemon(final Pokemon p) {
        return !p.getOffensiveSkills().isEmpty() && !p.getDefensiveSkills().isEmpty();
    }
}
