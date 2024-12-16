package edu.gatech.pokedome.modelprovider;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.pokedome.model.PokemonDisplayInfoModel;
import edu.gatech.pokedome.pokemon.PokemonModel;
import edu.gatech.pokedome.pokemon.PokemonRepository;
import lombok.RequiredArgsConstructor;

/**
 * This class provides the model for displaying Pokemon information.
 */
@RequiredArgsConstructor
public class PokemonInfoModelProvider {
    private final PokemonRepository repository;

    /**
     * Get the Pokemon display information. If the list of Pokemon names is empty, return all Pokemon information,
     * otherwise return the information for the Pokemon with the given names. Names are case-insensitive.
     * @param pokemonNames The list of Pokemon names to get information for.
     * @return The Pokemon display information.
     */
    public PokemonDisplayInfoModel get(final List<String> pokemonNames) {
        final Iterable<PokemonModel> allPokemons = repository.findAll();
        final List<String> lowercasedNames = pokemonNames.stream().map(String::toLowerCase).toList();
        final List<PokemonModel> filteredPokemons = new ArrayList<>();

        if (!lowercasedNames.isEmpty()) {
            allPokemons.forEach(pokemon -> {
                if (lowercasedNames.contains(pokemon.getNickname().toLowerCase())) {
                    filteredPokemons.add(pokemon);
                }
            });
        } else {
            allPokemons.forEach(filteredPokemons::add);
        }
        return PokemonDisplayInfoModel.builder().pokemons(filteredPokemons).build();
    }
}
