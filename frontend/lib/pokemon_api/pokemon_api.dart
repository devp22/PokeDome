import 'dart:convert';
import 'package:frontend/pokemon_api/http_utility.dart';
import 'package:frontend/pokemon_models/battle.dart';
import 'package:frontend/pokemon_models/pokemon.dart';
import 'package:frontend/pokemon_models/tournament.dart';

/// Provides a wrapper for the PokeDome API endpoints provided by the backend.
/// This is a singleton class.
class PokemonApi {
  static final PokemonApi _instance = PokemonApi._internal();

  // Hardcode the API origin as localhost:8080
  final HttpUtility _http = HttpUtility(origin: "localhost:8080");

  factory PokemonApi() {
    return _instance;
  }

  PokemonApi._internal();

  /// Initializes the dataholders.
  Future<void> init() async {
    await _getPokemonCatalogue();
  }

  /// Grabs all of the [Pokemon] from the backend and decodes them.
  /// The other data objects area decoded as well as the [Pokemon]
  /// object is parsing through them.
  Future<void> _getPokemonCatalogue() async {
    String json = await _http.getRequest("get-pokemon-catalogue");
    Pokemon.fromJsonList(jsonDecode(json));
  }

  /// Tells the backend to simulate a single battle with the provided
  /// [battle] parameters. The results are returned in String form.
  Future<String> simulateSingleBattle(Battle battle) async {
    String results = await _http.postRequest(
      "battle",
      jsonEncode(battle.toJson()),
    );
    return results;
  }

  /// Tells the backend to simulate a tournament with the provided
  /// [tournament] parameters. The results are returned in String form.
  Future<String> simulateTournament(Tournament tournament) async {
    String results = await _http.postRequest(
      "tournament",
      jsonEncode(tournament.toJson()),
    );
    return results;
  }
}
