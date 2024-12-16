package edu.gatech.pokedome.model;

import java.util.List;

import edu.gatech.pokedome.pokemon.PokemonModel;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class PokemonDisplayInfoModel {
    private List<PokemonModel> pokemons;
}
