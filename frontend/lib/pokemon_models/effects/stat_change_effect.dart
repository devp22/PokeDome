import 'package:frontend/pokemon_models/effects/effect.dart';

/// Dataholder for the backend [StatChangeEffect] class.
/// This [Effect] changes the using [Pokemon]'s defense by [defenseChange] and
/// attack by [attackChange] for [duration] turns, afterwards the changes revert.
class StatChangeEffect extends Effect {
  int defenseChange = 0;
  int attackChange = 0;
  int duration = 0;

  StatChangeEffect({
    required this.defenseChange,
    required this.attackChange,
    required this.duration,
  }) : super();

  /// Converts from the [jsonMap] JSON object to a [StatChangeEffect].
  static Effect fromJson(Map<String, Object?> jsonMap) {
    int defenseChange = jsonMap["defenseChange"] as int;
    int attackChange = jsonMap["attackChange"] as int;
    int duration = jsonMap["turnsActive"] as int;

    Effect? e = Effect.getSkillByDescription(
        _buildDescription(defenseChange, attackChange, duration));
    if (e != null) {
      // If there is already a [StatChangeEffect] with this description, don't
      // make a duplicate.
      return e;
    }

    return StatChangeEffect(
      defenseChange: defenseChange,
      attackChange: attackChange,
      duration: duration,
    );
  }

  @override

  /// Converts [StatChangeEffect] to a JSON object.
  Map<String, Object?> toJson() => {
        "effectType": "StatChangeEffect",
        "defenseChange": defenseChange,
        "attackChange": attackChange,
        "turnsActive": duration,
      };

  @override

  /// Returns this class' description.
  String getDescription() {
    return _buildDescription(defenseChange, attackChange, duration);
  }

  /// Builds a unique description for this class.
  static String _buildDescription(
      int defenseChange, int attackChange, int duration) {
    return "Defense Change: $defenseChange, Attack Change: $attackChange, Duration: $duration turn(s)";
  }
}
