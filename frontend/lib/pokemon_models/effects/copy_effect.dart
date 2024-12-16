import 'package:frontend/pokemon_models/effects/effect.dart';

/// Dataholder for the backend [CopyEffect] class.
/// This [Effect] copies the [Effect] most recently used by the enemy [Pokemon].
class CopyEffect extends Effect {
  static const String _description =
      "Copy effect of the enemy pokemon's most recently used skill";
  CopyEffect() : super();

  /// Converts from the [jsonMap] JSON object to a [CopyEffect].
  static Effect fromJson(Map<String, Object?> jsonMap) {
    Effect? e = Effect.getSkillByDescription(_description);
    if (e != null) {
      // If there is already a [CopyEffect] with this description, don't
      // make a duplicate.
      return e;
    }

    return CopyEffect();
  }

  @override

  /// Converts [CopyEffect] to a JSON object.
  Map<String, Object?> toJson() => {
        "effectType": "CopyEffect",
      };

  @override

  /// Returns this class' description.
  String getDescription() {
    return _description;
  }
}
