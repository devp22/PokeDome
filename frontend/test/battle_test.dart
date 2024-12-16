import 'dart:convert';

import 'package:frontend/pokemon_models/battle.dart';

void main() {
  String jsonBattle = '''
  {
    "pokemons": [
        {
            "nickname": "Pikachu",
            "type": "grass",
            "baseStat": {
                "attack": 0,
                "defense": 0,
                "hitPoints": 20
            },
            "offensiveSkills": [
                {
                    "name": "tackle",
                    "attackerEffects": [
                        {
                            "type": "DamageEffect",
                            "damage": 5
                        }
                    ],
                    "defenderEffects": []
                },
                {
                    "name": "charge",
                    "attackerEffects": [
                        {
                            "type": "DamageEffect",
                            "damage": 5
                        }
                    ],
                    "defenderEffects": []
                }
            ],
            "defensiveSkills": [
                {
                    "name": "block",
                    "attackerEffects": [],
                    "defenderEffects": [
                        {
                            "type": "DefenseEffect",
                            "defenseChange": 5,
                            "turnsActive": 1
                        }
                    ]
                },
                {
                    "name": "protect",
                    "attackerEffects": [],
                    "defenderEffects": [
                        {
                            "type": "DefenseEffect",
                            "defenseChange": 5,
                            "turnsActive": 1
                        }
                    ]
                }
            ]
        },
        {
            "nickname": "Charizard",
            "type": "grass",
            "baseStat": {
                "attack": 0,
                "defense": 0,
                "hitPoints": 20
            },
            "offensiveSkills": [
                {
                    "name": "tackle",
                    "attackerEffects": [
                        {
                            "type": "DamageEffect",
                            "damage": 5
                        }
                    ],
                    "defenderEffects": []
                },
                {
                    "name": "charge",
                    "attackerEffects": [
                        {
                            "type": "DamageEffect",
                            "damage": 5
                        }
                    ],
                    "defenderEffects": []
                }
            ],
            "defensiveSkills": [
                {
                    "name": "block",
                    "attackerEffects": [],
                    "defenderEffects": [
                        {
                            "type": "DefenseEffect",
                            "defenseChange": 5,
                            "turnsActive": 1
                        }
                    ]
                },
                {
                    "name": "protect",
                    "attackerEffects": [],
                    "defenderEffects": [
                        {
                            "type": "DefenseEffect",
                            "defenseChange": 5,
                            "turnsActive": 1
                        }
                    ]
                }
            ]
        }
    ],
    "seed": 8
  }
  ''';

  Battle battle = Battle.fromJson(jsonDecode(jsonBattle));
  print(jsonEncode(battle.toJson()));
}
