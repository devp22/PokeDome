import 'package:frontend/pokemon_models/effects/effect.dart';

/// Dataholder for the backend [DamageOverTimeEffect] class.
/// This [Effect] damages the enemy [Pokemon] by [damage] over a period of
/// [duration] turns.
class DamageOverTimeEffect extends Effect {
  int damage = 0;
  int duration = 0;

  DamageOverTimeEffect({
    required this.damage,
    required this.duration,
  }) : super();

  /// Converts from the [jsonMap] JSON object to a [DamageOverTimeEffect].
  static Effect fromJson(Map<String, Object?> jsonMap) {
    int damage = jsonMap["damage"] as int;
    int duration = jsonMap["turnsActive"] as int;

    Effect? e =
        Effect.getSkillByDescription(_buildDescription(damage, duration));
    if (e != null) {
      // If there is already a [DamageOverTimeEffect] with this description, don't
      // make a duplicate.
      return e;
    }

    return DamageOverTimeEffect(
      damage: damage,
      duration: duration,
    );
  }

  @override

  /// Converts [DamageOverTimeEffect] to a JSON object.
  Map<String, Object?> toJson() => {
        "effectType": "DamageOverTimeEffect",
        "damage": damage,
        "turnsActive": duration,
      };

  @override

  /// Returns this class' description.
  String getDescription() {
    return _buildDescription(damage, duration);
  }

  /// Builds a unique description for this class.
  static String _buildDescription(int damage, int duration) {
    return "Damage: $damage, Duration: $duration turn(s)";
  }
}
