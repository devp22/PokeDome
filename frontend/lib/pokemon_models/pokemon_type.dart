// ignore_for_file: constant_identifier_names

/// Data holder for backend PokemonType class.
/// Adds typing to [Pokemon] and [Skill]s. This typing affects how much damage
/// is done during hit point lowering moves.
enum PokemonType {
  FIRE,
  WATER,
  GRASS,
  NORMAL;

  /// Converts a String [type] to a [PokemonType]
  static PokemonType fromString(String type) {
    for (PokemonType value in values) {
      if (value.toString().trim().toLowerCase() == type.trim().toLowerCase()) {
        return value;
      }
    }
    return NORMAL;
  }

  @override
  // Converts a [PokemonType] to a String
  String toString() {
    return name;
  }
}
