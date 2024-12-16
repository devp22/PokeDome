import 'package:flutter/material.dart';
import 'package:frontend/pokemon_api/pokemon_api.dart';
import 'package:frontend/pokemon_models/effects/effect.dart';
import 'package:frontend/pokemon_models/skill.dart';

/// This screen is used to view, edit, and create [Skill]s.
class ManageSkills extends StatefulWidget {
  const ManageSkills({super.key});

  @override
  ManageSkillsState createState() => ManageSkillsState();
}

class ManageSkillsState extends State<ManageSkills> {
  PokemonApi api = PokemonApi();
  int skillIndex = 0;
  List<Effect> selectedEffects = [];
  TextEditingController nameController = TextEditingController();

  @override
  void initState() {
    super.initState();
    updateFields();
  }

  void updateFields() {
    selectedEffects = List.of(Skill.catalogue[skillIndex].effects);
    nameController.text = Skill.catalogue[skillIndex].name;
  }

  @override
  Widget build(BuildContext context) {
    Skill selectedSkill = Skill.catalogue[skillIndex];
    return Scaffold(
      appBar: AppBar(
        title: const Text("Manage Skills"),
        backgroundColor: Colors.yellow,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            ElevatedButton(
              onPressed: () {
                String newName = "New Skill";
                int index = 1;
                while (Skill.getSkillByName(newName) != null) {
                  newName = "New Skill $index";
                  index++;
                }
                Skill(
                  name: newName,
                  effects: [],
                );
                setState(() {});
              },
              child: const Text("Create New Skill"),
            ),
            const SizedBox(height: 20),
            const SizedBox(height: 20),
            DropdownButton<int>(
              hint: const Text("Select a Skill"),
              value: skillIndex,
              onChanged: (int? value) {
                if (value == null) {
                  return;
                }
                setState(() {
                  skillIndex = value;
                  updateFields();
                });
              },
              items: List.generate(
                Skill.catalogue.length,
                (int index) {
                  return DropdownMenuItem<int>(
                    value: index,
                    child: Text(
                      Skill.catalogue[index].name,
                    ),
                  );
                },
              ),
            ),
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
              ],
            ),
            const SizedBox(height: 20),
            Expanded(
              child: Align(
                alignment: Alignment.center,
                child: SingleChildScrollView(
                  child: DataTable(
                    dataRowMaxHeight: double.infinity,
                    columns: const [
                      DataColumn(label: Text('Skill')),
                      DataColumn(label: Text('Effects')),
                    ],
                    rows: List.generate(
                      Effect.catalogue.length,
                      (int index) {
                        Effect effect = Effect.catalogue[index];
                        return DataRow(
                          cells: [
                            DataCell(
                              Text(
                                effect.getDescription(),
                              ),
                            ),
                            DataCell(
                              Checkbox(
                                value: selectedEffects.contains(effect),
                                onChanged: (bool? value) {
                                  setState(
                                    () {
                                      if (value == true) {
                                        selectedEffects.add(effect);
                                      } else {
                                        selectedEffects.remove(effect);
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
                ),
              ),
            ),
            ElevatedButton(
              onPressed: () {
                selectedSkill.effects = selectedEffects;
                selectedSkill.name = nameController.text;

                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(
                      content: Text("Skill effects updated successfully")),
                );

                setState(() {});
              },
              child: const Text("Save Skills"),
            ),
          ],
        ),
      ),
    );
  }
}
