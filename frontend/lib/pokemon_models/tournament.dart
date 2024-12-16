import 'package:frontend/pokemon_models/pokemon.dart';

/// Data holder for the backend Tournament class.
/// A [Tournament] is a battle between 4 or more [Pokemon].
/// [Tournament]s can proceed in differing ways depending upon the type
/// that is chosen.
class Tournament {
  static List<String> types = [
    "Round Robin",
    "Single Elimination",
    "Double Elimination"
  ];
  List<Pokemon> pokemons = [];
  String type = Tournament.types[0];
  int? seed = 0;

  Tournament({
    required this.pokemons,
    required this.type,
    required this.seed,
  });

  /// Converts between the [jsonMap] JSON object to a [Tournament]
  Tournament.fromJson(Map<String, Object?> jsonMap) {
    List<Object?> jsonPokemons = jsonMap["pokemons"] as List<Object?>;
    pokemons = [];
    for (Object? jsonPokemon in jsonPokemons) {
      pokemons.add(
        Pokemon.fromJson(jsonPokemon as Map<String, Object?>),
      );
    }
    seed = jsonMap["seed"] as int;
    String enumType = jsonMap["tournamentType"] as String;
    for (String properType in Tournament.types) {
      if (enumType == properType.replaceAll(" ", "").toLowerCase()) {
        type = properType;
      }
    }
  }

  /// Converts a [Tournament] to a JSON object.
  Map<String, Object?> toJson() => {
        "pokemons": List.generate(
          pokemons.length,
          (int index) => pokemons[index].toJson(),
        ),
        "seed": seed,
        "tournamentType": type.replaceAll(" ", "").toLowerCase(),
      };
}
