import 'package:frontend/pokemon_models/effects/effect.dart';

/// Dataholder for the backend [ParalysisEffect] class.
/// This [Effect] has a chance of skipping the opponent [Pokemon]'s turn
/// each turn for [duration] turns.
class ParalysisEffect extends Effect {
  int duration = 0;

  ParalysisEffect({
    required this.duration,
  }) : super();

  /// Converts from the [jsonMap] JSON object to a [ParalysisEffect].
  static Effect fromJson(Map<String, Object?> jsonMap) {
    int duration = jsonMap["turnsActive"] as int;

    Effect? e = Effect.getSkillByDescription(_buildDescription(duration));
    if (e != null) {
      // If there is already a [ParalysisEffect] with this description, don't
      // make a duplicate.
      return e;
    }

    return ParalysisEffect(
      duration: duration,
    );
  }

  @override

  /// Converts [ParalysisEffect] to a JSON object.
  Map<String, Object?> toJson() => {
        "effectType": "ParalysisEffect",
        "turnsActive": duration,
      };

  @override

  /// Returns this class' description.
  String getDescription() {
    return _buildDescription(duration);
  }

  /// Builds a unique description for this class.
  static String _buildDescription(int duration) {
    return "Chance of paralysis for $duration turn(s)";
  }
}
