package edu.gatech.pokedome.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.gatech.pokedome.helper.JsonHelper;
import edu.gatech.pokedome.model.PokemonDisplayInfoModel;
import edu.gatech.pokedome.modelprovider.PokemonInfoModelProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * Controller for handling requests to get Pokemon catalogue information.
 */
@RestController
@RequiredArgsConstructor
public class PokemonCatalogueController {
    private final PokemonInfoModelProvider modelProvider;
    private final JsonHelper jsonHelper;

    /**
     * Handles GET requests to /get-pokemon-catalogue.
     * The request should contain a query parameter "pokemonNames" with a comma-separated list of Pokemon names.
     * If the parameter is not provided, the method will return information for all Pokemon.
     *
     * @param request the HTTP request
     * @return PokemonDisplayInfoModel containing information about the requested Pokemon
     */
    @RequestMapping(
        value = "/get-pokemon-catalogue",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> getPokemonCatalogue(final HttpServletRequest request) {
        final String pokenameNamesParam = request.getParameter("pokemonNames");
        final List<String> pokemonNames;
        if (pokenameNamesParam != null) {
            pokemonNames = List.of(pokenameNamesParam.split(","));
        } else {
            pokemonNames = List.of();
        }
        final PokemonDisplayInfoModel infoModel = modelProvider.get(pokemonNames);
        return ResponseEntity.ok(jsonHelper.toJson(infoModel));
    }
}
