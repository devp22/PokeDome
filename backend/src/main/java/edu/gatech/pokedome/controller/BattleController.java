package edu.gatech.pokedome.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.gatech.pokedome.battle.BattleSimulator;
import edu.gatech.pokedome.controller.dto.PokemonDTO;
import edu.gatech.pokedome.controller.dto.converter.PokemonConverter;
import edu.gatech.pokedome.pokemon.Pokemon;
import lombok.RequiredArgsConstructor;

/**
 * Controller for handling requests to start a battle.
 */
@RestController
@RequiredArgsConstructor
public class BattleController {
    final PokemonConverter pokemonConverter;

    /**
     * Handles POST requests to /battle. The request body should contain a list of two PokemonDTOs and an optional seed.
     * The method will convert the DTOs to Pokemon entities and then simulate a battle between the two.
     * The battle log will be returned as a response.
     *
     * @param request BattleRequest containing the two PokemonDTOs and optional seed for the battle
     * @return battle log
     */
    @RequestMapping(
        value = "/battle",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> battle(@RequestBody final BattleRequest request) {
        System.out.println(request);

        final List<PokemonDTO> pokemons = request.getPokemons();
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

        final StringBuffer battleLogBuffer = new StringBuffer();
        battleLogBuffer.append("Welcome to the PokeDome!\n");
        final BattleSimulator battleSimulator = new BattleSimulator(request.getSeed());
        battleSimulator.battle(battlePokemons.get(0), battlePokemons.get(1), battleLogBuffer);

        System.out.println(battleLogBuffer.toString());
        return ResponseEntity.ok(battleLogBuffer.toString());
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
