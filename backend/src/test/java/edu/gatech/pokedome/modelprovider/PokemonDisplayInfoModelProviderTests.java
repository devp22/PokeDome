package edu.gatech.pokedome.modelprovider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doReturn;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.gatech.pokedome.model.PokemonDisplayInfoModel;
import edu.gatech.pokedome.pokemon.PokemonModel;
import edu.gatech.pokedome.pokemon.PokemonRepository;

@ExtendWith(MockitoExtension.class)
public class PokemonDisplayInfoModelProviderTests {
    @Mock
    private PokemonRepository repository;

    @InjectMocks
    private PokemonInfoModelProvider modelProvider;

    @Test
    public void testGet_whenEmptyList_expectAllPokemons() {
        // Arrange
        final List<PokemonModel> pokemons = List.of(
            PokemonModel.builder().nickname("Pikachu").type("Water").build(),
            PokemonModel.builder().nickname("Charmander").type("Fire").build()
        );
        doReturn(pokemons).when(repository).findAll();

        // Act
        final PokemonDisplayInfoModel result = modelProvider.get(List.of());

        // Assert
        assertThat(result.getPokemons(), equalTo(pokemons));
    }

    @Test
    public void testGet_whenSinglenickname_expectOnePokemon() {
        final List<PokemonModel> pokemons = List.of(
            PokemonModel.builder().nickname("Pikachu").type("Water").build(),
            PokemonModel.builder().nickname("Charmander").type("Fire").build()
        );
        doReturn(pokemons).when(repository).findAll();

        final PokemonDisplayInfoModel result = modelProvider.get(List.of("Pikachu"));
        assertThat(result.getPokemons().size(), equalTo(1));
        assertThat(result.getPokemons().getFirst().getNickname(), equalTo("Pikachu"));
    }

    @Test
    public void testGet_whenMultiplenicknames_expectMultiplePokemon() {
        // Arrange
        final List<PokemonModel> pokemons = List.of(
            PokemonModel.builder().nickname("Pikachu").type("Water").build(),
            PokemonModel.builder().nickname("Charmander").type("Fire").build()
        );
        doReturn(pokemons).when(repository).findAll();

        final PokemonDisplayInfoModel result = modelProvider.get(List.of("Pikachu", "Charmander"));
        assertThat(result.getPokemons(), equalTo(pokemons));
    }

    @Test
    public void testGet_whenLowercasenickname_expectPokemon() {
        final List<PokemonModel> pokemons = List.of(
            PokemonModel.builder().nickname("Pikachu").type("Water").build(),
            PokemonModel.builder().nickname("Charmander").type("Fire").build()
        );
        doReturn(pokemons).when(repository).findAll();

        final PokemonDisplayInfoModel result = modelProvider.get(List.of("pikachu"));
        assertThat(result.getPokemons().size(), equalTo(1));
        assertThat(result.getPokemons().getFirst().getNickname(), equalTo("Pikachu"));
    }
}
