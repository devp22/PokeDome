import 'package:flutter/material.dart';
import 'package:frontend/pokemon_api/pokemon_api.dart';
import 'package:frontend/pokemon_models/base_stat.dart';
import 'package:frontend/pokemon_models/hit_point_strategy.dart';
import 'package:frontend/pokemon_models/pokemon.dart';
import 'package:frontend/pokemon_models/pokemon_type.dart';
import 'package:frontend/pokemon_models/skill.dart';

/// This screen is used to view, edit, and create [Pokemmon]s.
class ViewPokemon extends StatelessWidget {
  const ViewPokemon({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("View Pokemon"),
        backgroundColor: Colors.yellow,
      ),
      body: const SingleChildScrollView(
        child: Center(
          child: Padding(
            padding: EdgeInsets.all(16.0),
            child: PokemonDropdown(),
          ),
        ),
      ),
    );
  }
}

class PokemonDropdown extends StatefulWidget {
  const PokemonDropdown({super.key});

  @override
  State<PokemonDropdown> createState() => _PokemonDropdownState();
}

class _PokemonDropdownState extends State<PokemonDropdown> {
  PokemonApi api = PokemonApi();
  int pokemonIndex = 0;
  PokemonType selectedType = PokemonType.values[0];
  List<Skill> selectedAttackSkills = [];
  List<Skill> selectedDefenseSkills = [];
  TextEditingController hitPointsController = TextEditingController();
  TextEditingController attackController = TextEditingController();
  TextEditingController defenseController = TextEditingController();
  TextEditingController nameController = TextEditingController();
  bool isSkillCardExpanded = false;
  double aggressionSliderValue = 5.0;
  double cautionSliderValue = 5.0;

  @override
  void initState() {
    super.initState();
    updateFields();
  }

  @override
  void dispose() {
    hitPointsController.dispose();
    attackController.dispose();
    defenseController.dispose();
    super.dispose();
  }

  void savePokemonDetails() {
    Pokemon selectedPokemon = Pokemon.catalogue[pokemonIndex];
    selectedPokemon.type = selectedType;
    selectedPokemon.offensiveSkills = List.of(selectedAttackSkills);
    selectedPokemon.defensiveSkills = List.of(selectedDefenseSkills);
    selectedPokemon.baseStat.hitPoints =
        int.tryParse(hitPointsController.text) ??
            selectedPokemon.baseStat.hitPoints;
    selectedPokemon.baseStat.attack =
        int.tryParse(attackController.text) ?? selectedPokemon.baseStat.attack;
    selectedPokemon.baseStat.defense = int.tryParse(defenseController.text) ??
        selectedPokemon.baseStat.defense;
    selectedPokemon.nickname = nameController.text;
    selectedPokemon.hitPointStrategy.aggression = aggressionSliderValue.toInt();
    selectedPokemon.hitPointStrategy.caution = cautionSliderValue.toInt();

    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(
          content: Text("Pokemon details and stats updated successfully")),
    );
  }

  void updateFields() {
    Pokemon selectedPokemon = Pokemon.catalogue[pokemonIndex];
    selectedType = selectedPokemon.type;
    selectedAttackSkills = List.of(selectedPokemon.offensiveSkills);
    selectedDefenseSkills = List.of(selectedPokemon.defensiveSkills);
    hitPointsController.text = selectedPokemon.baseStat.hitPoints.toString();
    attackController.text = selectedPokemon.baseStat.attack.toString();
    defenseController.text = selectedPokemon.baseStat.defense.toString();
    nameController.text = selectedPokemon.nickname;
    aggressionSliderValue =
        selectedPokemon.hitPointStrategy.aggression.toDouble();
    cautionSliderValue = selectedPokemon.hitPointStrategy.caution.toDouble();
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        ElevatedButton(
          onPressed: () {
            String newName = "New Pokemon";
            int index = 1;
            while (Pokemon.getPokemonByNickname(newName) != null) {
              newName = "New Pokemon $index";
              index++;
            }
            Pokemon(
              nickname: newName,
              type: PokemonType.values[0],
              baseStat: BaseStat(
                hitPoints: 100,
                attack: 50,
                defense: 50,
              ),
              offensiveSkills: [],
              defensiveSkills: [],
              hitPointStrategy: HitPointStrategy(
                aggression: 5,
                caution: 5,
              ),
            );
            setState(() {});
          },
          child: const Text("Create New Pokemon"),
        ),
        const SizedBox(height: 20),
        DropdownButton<int>(
          hint: const Text("Select a Pokemon"),
          value: pokemonIndex,
          onChanged: (int? value) {
            if (value == null) {
              return;
            }
            setState(() {
              pokemonIndex = value;
              updateFields();
            });
          },
          items: List.generate(
            Pokemon.catalogue.length,
            (int index) {
              return DropdownMenuItem<int>(
                value: index,
                child: Text(Pokemon.catalogue[index].nickname),
              );
            },
          ),
        ),
        const SizedBox(height: 20),
        Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Table(
              defaultVerticalAlignment: TableCellVerticalAlignment.middle,
              columnWidths: const {
                0: FixedColumnWidth(150),
                1: FixedColumnWidth(200)
              },
              children: [
                TableRow(children: [
                  const TableCell(child: Text("Pokemon Name")),
                  TableCell(
                    child: TextField(
                      controller: nameController,
                      decoration: const InputDecoration(
                        border: OutlineInputBorder(),
                      ),
                    ),
                  ),
                ]),
                TableRow(children: [
                  const TableCell(child: Text("Type of Pokemon")),
                  TableCell(
                    child: DropdownButton<PokemonType>(
                      hint: const Text("Select Type of Pokemon"),
                      value: selectedType,
                      onChanged: (PokemonType? value) {
                        if (value == null) {
                          return;
                        }
                        setState(() {
                          selectedType = value;
                        });
                      },
                      items: List.generate(
                        PokemonType.values.length,
                        (int index) {
                          return DropdownMenuItem<PokemonType>(
                            value: PokemonType.values[index],
                            child: Text(PokemonType.values[index].toString()),
                          );
                        },
                      ),
                    ),
                  ),
                ]),
              ],
            ),
            const SizedBox(height: 20),
            Container(
              padding: const EdgeInsets.all(16.0),
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(10),
                border: Border.all(color: Colors.black, width: 1),
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const Text(
                    "Hit Point Strategy",
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                      fontSize: 25,
                    ),
                  ),
                  const SizedBox(height: 50),
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        "Aggression: ${aggressionSliderValue.toStringAsFixed(0)}",
                        style: const TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 14,
                        ),
                      ),
                      Slider(
                        value: aggressionSliderValue,
                        min: 1,
                        max: 10,
                        divisions: 9,
                        onChanged: (double value) {
                          setState(() {
                            aggressionSliderValue = value;
                          });
                        },
                      ),
                      const Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Text("1 - Least Aggressive"),
                          Text("10 - Most Aggressive"),
                        ],
                      ),
                      const SizedBox(height: 20),
                      Text(
                        "Cautiousness: ${cautionSliderValue.toStringAsFixed(0)}",
                        style: const TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 14,
                        ),
                      ),
                      Slider(
                        value: cautionSliderValue,
                        min: 1,
                        max: 10,
                        divisions: 9,
                        onChanged: (double value) {
                          setState(() {
                            cautionSliderValue = value;
                          });
                        },
                      ),
                      const Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Text("1 - Least Aggressive"),
                          Text("10 - Most Aggressive"),
                        ],
                      ),
                    ],
                  ),
                ],
              ),
            ),
            const SizedBox(height: 20),
            Table(
              defaultVerticalAlignment: TableCellVerticalAlignment.middle,
              columnWidths: const {
                0: FixedColumnWidth(100),
                1: FixedColumnWidth(50)
              },
              children: [
                TableRow(children: [
                  const TableCell(child: Text("Hit Points")),
                  TableCell(child: buildEditableTextField(hitPointsController)),
                ]),
                TableRow(children: [
                  const TableCell(child: Text("Attack")),
                  TableCell(child: buildEditableTextField(attackController)),
                ]),
                TableRow(children: [
                  const TableCell(child: Text("Defense")),
                  TableCell(child: buildEditableTextField(defenseController)),
                ]),
              ],
            ),
            const SizedBox(height: 20),
            Row(
              mainAxisSize: MainAxisSize.min,
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Expanded(
                  child: Card(
                    elevation: 2,
                    child: Column(
                      children: [
                        ListTile(
                          title: const Text("Select Skills"),
                          trailing: Icon(
                            isSkillCardExpanded
                                ? Icons.expand_less
                                : Icons.expand_more,
                          ),
                          onTap: () {
                            setState(() {
                              isSkillCardExpanded = !isSkillCardExpanded;
                            });
                          },
                        ),
                        if (isSkillCardExpanded)
                          DataTable(
                            dataRowMaxHeight: double.infinity,
                            columns: const [
                              DataColumn(label: Text('Skill')),
                              DataColumn(label: Text('Offensive')),
                              DataColumn(label: Text('Defensive')),
                            ],
                            rows: List.generate(
                              Skill.catalogue.length,
                              (int index) {
                                Skill skill = Skill.catalogue[index];
                                return DataRow(
                                  cells: [
                                    DataCell(
                                      Text(
                                        skill.getDescription(),
                                      ),
                                    ),
                                    DataCell(
                                      Checkbox(
                                        value: selectedAttackSkills
                                            .contains(skill),
                                        onChanged: (bool? value) {
                                          setState(
                                            () {
                                              if (value == true) {
                                                selectedAttackSkills.add(skill);
                                                selectedDefenseSkills
                                                    .remove(skill);
                                              } else {
                                                selectedAttackSkills
                                                    .remove(skill);
                                              }
                                            },
                                          );
                                        },
                                      ),
                                    ),
                                    DataCell(
                                      Checkbox(
                                        value: selectedDefenseSkills
                                            .contains(skill),
                                        onChanged: (bool? value) {
                                          setState(
                                            () {
                                              if (value == true) {
                                                selectedDefenseSkills
                                                    .add(skill);
                                                selectedAttackSkills
                                                    .remove(skill);
                                              } else {
                                                selectedDefenseSkills
                                                    .remove(skill);
                                              }
                                            },
                                          );
                                        },
                                      ),
                                    ),
                                  ],
                                );
                              },
                            ),
                          ),
                      ],
                    ),
                  ),
                ),
              ],
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                savePokemonDetails();
                setState(() {});
              },
              child: const Text("Save Pokemon Details"),
            ),
          ],
        ),
      ],
    );
  }

  TextField buildEditableTextField(TextEditingController controller) {
    return TextField(
      controller: controller,
      textAlign: TextAlign.center,
      keyboardType: TextInputType.number,
      decoration: const InputDecoration(
        border: OutlineInputBorder(),
        contentPadding: EdgeInsets.symmetric(horizontal: 8, vertical: 4),
      ),
    );
  }
}
