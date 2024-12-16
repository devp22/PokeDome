import 'package:frontend/pokemon_models/base_stat.dart';
import 'package:frontend/pokemon_models/hit_point_strategy.dart';
import 'package:frontend/pokemon_models/pokemon_type.dart';
import 'package:frontend/pokemon_models/skill.dart';

/// Data holder for the backend Pokemon class.
class Pokemon {
  // List of all known [Pokemon]
  static List<Pokemon> catalogue = [];
  String nickname = "";
  PokemonType type = PokemonType.values[0];
  List<Skill> offensiveSkills = [];
  List<Skill> defensiveSkills = [];
  late BaseStat baseStat;
  int wins = 0;
  int losses = 0;
  HitPointStrategy hitPointStrategy;

  Pokemon({
    required this.nickname,
    required this.type,
    required this.baseStat,
    required this.offensiveSkills,
    required this.defensiveSkills,
    required this.hitPointStrategy,
  }) {
    Pokemon.catalogue.add(this);
  }

  /// Returns a [Pokemon] from the catalogue if it matches [nickname].
  /// A [Pokemon]'s [nickname] is used to uniquely identify it.
  static Pokemon? getPokemonByNickname(String nickname) {
    for (Pokemon p in Pokemon.catalogue) {
      if (p.nickname == nickname) {
        return p;
      }
    }
    return null;
  }

  /// Parses a list of [Pokemon] provided by the backend API call.
  /// Each [Pokemon] in this list also conains [Skill]s and [Effect]s. These
  /// objects are also parsed as part of this method.
  static void fromJsonList(Map<String, Object?> jsonMap) {
    if (!jsonMap.containsKey("pokemons")) {
      return;
    }
    List<Object?> jsonList = jsonMap["pokemons"] as List<Object?>;
    for (Object? jsonObject in jsonList) {
      if (jsonObject is Map<String, Object?>) {
        fromJson(jsonObject);
      }
    }
  }

  /// Converts between the [jsonMap] JSON object to a [Pokemon]
  static Pokemon fromJson(Map<String, Object?> jsonMap) {
    String nickname = jsonMap["nickname"] as String;

    Pokemon? p = Pokemon.getPokemonByNickname(nickname);
    if (p != null) {
      // If there is already a [Pokemon] with this nickname, don't
      // make a duplicate.
      return p;
    }

    PokemonType type = PokemonType.fromString(jsonMap["type"] as String);
    BaseStat baseStat =
        BaseStat.fromJson(jsonMap["baseStats"] as Map<String, Object?>);
    HitPointStrategy hitPointStrategy = HitPointStrategy.fromJson(
        jsonMap["hitPointStrategy"] as Map<String, Object?>);

    List<Skill> offensiveSkills = [];
    for (Object? jsonSkill in jsonMap["offensiveSkills"] as List<Object?>) {
      Skill offensiveSkill = Skill.fromJson(jsonSkill as Map<String, Object?>);
      offensiveSkills.add(offensiveSkill);
    }

    List<Skill> defensiveSkills = [];
    for (Object? jsonSkill in jsonMap["defensiveSkills"] as List<Object?>) {
      Skill defensiveSkill = Skill.fromJson(jsonSkill as Map<String, Object?>);
      defensiveSkills.add(defensiveSkill);
    }

    return Pokemon(
      nickname: nickname,
      type: type,
      baseStat: baseStat,
      offensiveSkills: offensiveSkills,
      defensiveSkills: defensiveSkills,
      hitPointStrategy: hitPointStrategy,
    );
  }

  /// Converts a [Pokemon] to a JSON object.
  Map<String, Object?> toJson() => {
        "nickname": nickname,
        "type": type.toString(),
        "baseStat": baseStat.toJson(),
        "offensiveSkills": List.generate(
          offensiveSkills.length,
          (int index) => offensiveSkills[index].toJson(),
        ),
        "defensiveSkills": List.generate(
          defensiveSkills.length,
          (int index) => defensiveSkills[index].toJson(),
        ),
        "hitPointStrategy": hitPointStrategy.toJson(),
      };
}
