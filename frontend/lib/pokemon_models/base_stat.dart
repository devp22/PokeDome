/// Data holder for the backend [StatGroup] class.
/// Holds a [Pokemon]'s stats.
class BaseStat {
  int hitPoints = 0;
  int attack = 0;
  int defense = 0;

  BaseStat({
    required this.hitPoints,
    required this.attack,
    required this.defense,
  });

  /// Converts from the [jsonMap] JSON object to a [BaseStat].
  BaseStat.fromJson(Map<String, Object?> jsonMap) {
    hitPoints = jsonMap["hitPoints"] as int;
    attack = jsonMap["attack"] as int;
    defense = jsonMap["defense"] as int;
  }

  /// Converts [BaseStat] to a JSON object.
  Map<String, Object?> toJson() => {
        "hitPoints": hitPoints,
        "attack": attack,
        "defense": defense,
      };
}
