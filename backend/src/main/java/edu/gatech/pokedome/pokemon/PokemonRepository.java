package edu.gatech.pokedome.pokemon;

import org.springframework.data.repository.CrudRepository;

/**
 * Database repository to store various template Pokemons that can be used in battle.
 */
public interface PokemonRepository extends CrudRepository<PokemonModel, Long> {
}
