// import 'dart:convert';
// import 'package:frontend/pokemon_models/base_stat.dart';
// import 'package:frontend/pokemon_models/catalogue.dart';
// import 'package:frontend/pokemon_models/effects/damage_effect.dart';
// import 'package:frontend/pokemon_models/effects/defense_effect.dart';
// import 'package:frontend/pokemon_models/effects/effect.dart';
// import 'package:frontend/pokemon_models/pokemon.dart';
// import 'package:frontend/pokemon_models/skill.dart';

// void main() {
//   // Define Damage Effects
//   Effect damage50 = DamageEffect(
//     damage: 50,
//   );
//   Effect damage30 = DamageEffect(
//     damage: 30,
//   );
//   Effect damage20 = DamageEffect(
//     damage: 20,
//   );
//   Effect damage10 = DamageEffect(
//     damage: 10,
//   );

//   // Define Defense Effects
//   Effect defense50 = DefenseEffect(
//     defenseChange: 50,
//     duration: 1,
//   );
//   Effect defense30 = DefenseEffect(
//     defenseChange: 30,
//     duration: 1,
//   );
//   Effect defense20 = DefenseEffect(
//     defenseChange: 20,
//     duration: 1,
//   );
//   Effect defense10 = DefenseEffect(
//     defenseChange: 10,
//     duration: 1,
//   );

//   EffectCatalogue effectCatalogue = EffectCatalogue.fromJson(
//     Map.of(
//       {
//         "effects": List.of(
//           [
//             damage50.toJson(),
//             damage30.toJson(),
//             damage20.toJson(),
//             damage10.toJson(),
//             defense50.toJson(),
//             defense30.toJson(),
//             defense20.toJson(),
//             defense10.toJson(),
//           ],
//         ),
//       },
//     ),
//   );

//   // Define Skills
//   Skill flamethrower = Skill(
//     name: "Flamethrower",
//     attackerEffects: [damage50],
//     defenderEffects: [],
//   );
//   Skill thunderbolt = Skill(
//     name: "Thunderbolt",
//     attackerEffects: [damage30],
//     defenderEffects: [],
//   );
//   Skill slash = Skill(
//     name: "Slash",
//     attackerEffects: [damage20],
//     defenderEffects: [],
//   );
//   Skill tackle = Skill(
//     name: "Tackle",
//     attackerEffects: [damage10],
//     defenderEffects: [],
//   );
//   Skill protect = Skill(
//     name: "Protect",
//     attackerEffects: [],
//     defenderEffects: [defense50],
//   );
//   Skill block = Skill(
//     name: "Block",
//     attackerEffects: [],
//     defenderEffects: [defense30],
//   );
//   Skill shield = Skill(
//     name: "Shield",
//     attackerEffects: [],
//     defenderEffects: [defense20],
//   );
//   Skill charge = Skill(
//     name: "Charge",
//     attackerEffects: [],
//     defenderEffects: [defense10],
//   );

//   SkillCatalogue skillCatalogue = SkillCatalogue.fromJson(
//     Map.of(
//       {
//         "skills": List.of(
//           [
//             flamethrower.toJson(),
//             thunderbolt.toJson(),
//             slash.toJson(),
//             tackle.toJson(),
//             protect.toJson(),
//             block.toJson(),
//             shield.toJson(),
//             charge.toJson(),
//           ],
//         ),
//       },
//     ),
//   );

//   // Define Pokemon
//   Pokemon charizard = Pokemon(
//     nickname: "Charizard",
//     type: "fire",
//     baseStat: BaseStat(
//       hitPoints: 100,
//       attack: 80,
//       defense: 50,
//     ),
//     offensiveSkills: [flamethrower, slash],
//     defensiveSkills: [block, shield],
//   );
//   Pokemon pikachu = Pokemon(
//     nickname: "Pikachu",
//     type: "electric",
//     baseStat: BaseStat(
//       hitPoints: 80,
//       attack: 60,
//       defense: 60,
//     ),
//     offensiveSkills: [thunderbolt, tackle],
//     defensiveSkills: [protect, charge],
//   );

//   PokemonCatalogue pokemonCatalogue = PokemonCatalogue.fromJson(
//     Map.of(
//       {
//         "pokemon": List.of(
//           [
//             charizard.toJson(),
//             pikachu.toJson(),
//           ],
//         ),
//       },
//     ),
//   );

//   // Test if json is  correct
//   // print(jsonEncode(charizard.toJson()));
//   print(jsonEncode(pokemonCatalogue.toJson()));
// }
