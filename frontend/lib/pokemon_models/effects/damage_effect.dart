import 'package:frontend/pokemon_models/effects/effect.dart';
import 'package:frontend/pokemon_models/pokemon_type.dart';

/// Dataholder for the backend [DamageEffect] class.
/// This [Effect] damages the enemy [Pokemon] by [damage] hit points.
class DamageEffect extends Effect {
  int damage = 0;
  PokemonType type = PokemonType.values[0];

  DamageEffect({
    required this.damage,
    required this.type,
  }) : super();

  /// Converts from the [jsonMap] JSON object to a [DamageEffect].
  static Effect fromJson(Map<String, Object?> jsonMap) {
    int damage = jsonMap["damage"] as int;
    PokemonType type = PokemonType.fromString(jsonMap["type"] as String);

    Effect? e = Effect.getSkillByDescription(
      _buildDescription(damage, type),
    );
    if (e != null) {
      // If there is already a [DamageEffect] with this description, don't
      // make a duplicate.
      return e;
    }

    return DamageEffect(
      damage: damage,
      type: type,
    );
  }

  @override

  /// Converts [DamageEffect] to a JSON object.
  Map<String, Object?> toJson() => {
        "effectType": "DamageEffect",
        "damage": damage,
        "dmgType": type.toString(),
      };

  @override

  /// Returns this class' description.
  String getDescription() {
    return _buildDescription(damage, type);
  }

  /// Builds a unique description for this class.
  static String _buildDescription(int damage, PokemonType type) {
    return "Damage: $damage, Type: ${type.toString()}";
  }
}
