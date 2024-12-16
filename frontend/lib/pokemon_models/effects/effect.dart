import 'package:frontend/pokemon_models/effects/damage_effect.dart';
import 'package:frontend/pokemon_models/effects/damage_over_time_effect.dart';
import 'package:frontend/pokemon_models/effects/paralysis_effect.dart';
import 'package:frontend/pokemon_models/effects/stat_change_effect.dart';
import 'package:frontend/pokemon_models/effects/heal_effect.dart';
import 'package:frontend/pokemon_models/effects/copy_effect.dart';

/// Dataholder for the backend [Effect] class.
/// An [Effect] is some change that some [Pokemon] can make to another [Pokemon]
/// or to itself. It may be a positive or negative effect.
abstract class Effect {
  // The list of all known effects
  static List<Effect> catalogue = [];
  // A header that points the backend to the specific effect's implementation
  static const String typeHeader = "edu.gatech.pokedome.pokemon.effect.";

  Effect() {
    Effect.catalogue.add(this);
  }

  /// Converts from the [jsonMap] JSON object to a concrete [Effect].
  /// Defaults to a Damage effect.
  factory Effect.fromJson(Map<String, Object?> jsonMap) {
    String type = jsonMap["effectType"] as String;
    Map<String, Object?> jsonEffect = jsonMap["effect"] as Map<String, Object?>;
    switch (type) {
      case "${Effect.typeHeader}DamageEffect":
        return DamageEffect.fromJson(jsonEffect);
      case "${Effect.typeHeader}DamageOverTimeEffect":
        return DamageOverTimeEffect.fromJson(jsonEffect);
      case "${Effect.typeHeader}ParalysisEffect":
        return ParalysisEffect.fromJson(jsonEffect);
      case "${Effect.typeHeader}StatChangeEffect":
        return StatChangeEffect.fromJson(jsonEffect);
      case "${Effect.typeHeader}HealEffect":
        return HealEffect.fromJson(jsonEffect);
      case "${Effect.typeHeader}CopyEffect":
        return CopyEffect.fromJson(jsonEffect);
      default:
        return DamageEffect.fromJson(jsonEffect);
    }
  }

  /// Returns an [Effect] from the catalogue if it matches [description].
  /// An [Effect]'s [description] is used to uniquely identify it.
  static Effect? getSkillByDescription(String description) {
    for (Effect e in Effect.catalogue) {
      if (description == e.getDescription()) {
        return e;
      }
    }
    return null;
  }

  /// Abstract method for getting an [Effect]'s description.
  String getDescription();

  /// Abstract method for converting an [Effect] to a JSON object.
  Map<String, Object?> toJson();
}
