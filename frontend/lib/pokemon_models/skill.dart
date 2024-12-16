import 'package:frontend/pokemon_models/effects/effect.dart';

/// Data holder for the backend Skill class.
/// A [Skill] is an action that a [Pokemon] can take to apply [Effect]s to
/// enemy [Pokemon] or to itself.
class Skill {
  // List of all known [Skill]s
  static List<Skill> catalogue = [];
  String name = "";
  List<Effect> effects = [];

  Skill({
    required this.name,
    required this.effects,
  }) {
    Skill.catalogue.add(this);
  }

  /// Returns a [Skill] from the catalogue if it matches [name].
  /// A [Skill]'s [name] is used to uniquely identify it.
  static Skill? getSkillByName(String name) {
    for (Skill s in Skill.catalogue) {
      if (name == s.name) {
        return s;
      }
    }
    return null;
  }

  /// Converts between the [jsonMap] JSON object to a [Skill]
  static Skill fromJson(Map<String, Object?> jsonMap) {
    String name = jsonMap["name"] as String;

    Skill? s = Skill.getSkillByName(name);
    if (s != null) {
      return s;
    }

    List<Effect> effects = [];
    for (Object? jsonSkill in jsonMap["effects"] as List<Object?>) {
      Effect effect = Effect.fromJson(jsonSkill as Map<String, Object?>);
      effects.add(effect);
    }

    return Skill(
      name: name,
      effects: effects,
    );
  }

  /// Converts a [Skill] to a JSON object.
  Map<String, Object?> toJson() => {
        "name": name,
        "effects": List.generate(
          effects.length,
          (int index) => effects[index].toJson(),
        ),
      };

  /// Returns a description of this [Skill] along with descriptions
  /// of its [Effect]s for display on the GUI.
  String getDescription() {
    String description = "Name: $name";
    description += "\n Effects: ";
    if (effects.isEmpty) {
      description += "None";
    }
    for (Effect effect in effects) {
      description += "\n \t\t";
      description += effect.getDescription();
    }
    return description;
  }
}
