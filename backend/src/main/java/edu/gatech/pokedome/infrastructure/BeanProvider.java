package edu.gatech.pokedome.infrastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import edu.gatech.pokedome.PokedomeCommandLineRunner;
import edu.gatech.pokedome.controller.BattleController;
import edu.gatech.pokedome.controller.HelloWorldController;
import edu.gatech.pokedome.controller.PokemonCatalogueController;
import edu.gatech.pokedome.controller.TournamentController;
import edu.gatech.pokedome.controller.dto.converter.PokemonConverter;
import edu.gatech.pokedome.helper.JsonHelper;
import edu.gatech.pokedome.modelprovider.PokemonInfoModelProvider;
import edu.gatech.pokedome.pokemon.PokemonRepository;
import edu.gatech.pokedome.pokemon.effect.Effect;
import edu.gatech.pokedome.pokemon.modelConverters.EffectSerializer;
import edu.gatech.pokedome.tournament.TournamentSimulator;

/**
 * Provides beans for the application.
 */
@Configuration
@ComponentScan("edu.gatech.pokedome")
public class BeanProvider {
    @Bean
    public HelloWorldController helloWorldController() {
        return new HelloWorldController();
    }

    @Bean
    public JsonHelper jsonHelper() {
        return new JsonHelper(new GsonBuilder().registerTypeAdapter(Effect.class, new EffectSerializer()).create());
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public PokedomeCommandLineRunner pokedomeCommandLineRunner(final PokemonRepository repository) {
        return new PokedomeCommandLineRunner(repository);
    }

    @Bean
    public PokemonInfoModelProvider pokemonDisplayInfoModelProvider(final PokemonRepository repository) {
        return new PokemonInfoModelProvider(repository);
    }

    @Bean
    public PokemonConverter pokemonConverter() {
        return new PokemonConverter();
    }

    @Bean
    public BattleController battleController(final PokemonConverter pokemonConverter) {
        return new BattleController(pokemonConverter);
    }

    @Bean
    public PokemonCatalogueController pokemonCatalogueController(final PokemonInfoModelProvider modelProvider,
        final JsonHelper jsonHelper) {
        return new PokemonCatalogueController(modelProvider, jsonHelper);
    }

    @Bean
    public TournamentController tournamentController(final PokemonConverter pokemonConverter,
        final TournamentSimulator tournamentSimulator) {
        return new TournamentController(pokemonConverter, tournamentSimulator);
    }

    @Bean
    public TournamentSimulator tournamentSimulator() {
        return new TournamentSimulator();
    }

}
