package edu.gatech.pokedome.controller;

import java.util.List;

import org.springframework.lang.Nullable;

import edu.gatech.pokedome.controller.dto.PokemonDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TournamentRequest {
    private List<PokemonDTO> pokemons;
    private String tournamentType;
    @Nullable private Integer seed;
}
