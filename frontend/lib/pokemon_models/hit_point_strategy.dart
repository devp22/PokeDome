/// Data holder for the backend HitPointStrategy class.
/// Holds a few values [aggression] and [caution] which determine
/// how likely a [Pokemon] is to attack or defend on a given turn.
class HitPointStrategy {
  int aggression = 1;
  int caution = 1;

  HitPointStrategy({
    required this.aggression,
    required this.caution,
  });

  /// Converts from the [jsonMap] JSON object to a [HitPointStrategy].
  HitPointStrategy.fromJson(Map<String, Object?> jsonMap) {
    aggression = jsonMap["aggression"] as int;
    caution = jsonMap["caution"] as int;
  }

  /// Converts [HitPointStrategy] to a JSON object.
  Map<String, Object?> toJson() => {
        "aggression": aggression,
        "caution": caution,
      };
}
