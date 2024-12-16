import 'package:frontend/pokemon_models/effects/effect.dart';

/// Dataholder for the backend [HealEffect] class.
/// This [Effect] heals the current [Pokemon] by [heal] hit points.
class HealEffect extends Effect {
  int heal = 0;

  HealEffect({
    required this.heal,
  }) : super();

  /// Converts from the [jsonMap] JSON object to a [HealEffect].
  static Effect fromJson(Map<String, Object?> jsonMap) {
    int heal = jsonMap["heal"] as int;

    Effect? e = Effect.getSkillByDescription(_buildDescription(heal));
    if (e != null) {
      // If there is already a [HealEffect] with this description, don't
      // make a duplicate.
      return e;
    }

    return HealEffect(
      heal: heal,
    );
  }

  @override

  /// Converts [HealEffect] to a JSON object.
  Map<String, Object?> toJson() => {
        "effectType": "HealEffect",
        "heal": heal,
      };

  @override

  /// Returns this class' description.
  String getDescription() {
    return _buildDescription(heal);
  }

  /// Builds a unique description for this class.
  static String _buildDescription(int heal) {
    return "Heal: $heal";
  }
}
