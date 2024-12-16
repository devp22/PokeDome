import 'package:frontend/pokemon_models/pokemon.dart';

/// Data holder for the backend [Battle] class.
class Battle {
  late Pokemon first;
  late Pokemon second;
  int? seed = 0;

  Battle({
    required this.first,
    required this.second,
    required this.seed,
  });

  /// Converts from the [jsonMap] JSON object to a [Battle].
  Battle.fromJson(Map<String, Object?> jsonMap) {
    List<Object?> jsonPokemons = jsonMap["pokemons"] as List<Object?>;
    first = Pokemon.fromJson(jsonPokemons[0] as Map<String, Object?>);
    second = Pokemon.fromJson(jsonPokemons[1] as Map<String, Object?>);
    seed = jsonMap["seed"] as int;
  }

  /// Converts [Battle] to a JSON object.
  Map<String, Object?> toJson() => {
        "pokemons": List.of(
          [
            first.toJson(),
            second.toJson(),
          ],
        ),
        "seed": seed,
      };
}
