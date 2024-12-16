import 'package:flutter/material.dart';
import 'package:frontend/pokemon_api/pokemon_api.dart';
import 'package:frontend/pokemon_models/battle.dart';
import 'package:frontend/pokemon_models/pokemon.dart';

/// This screen is used to simulate single [Battle]s.
class SingleBattle extends StatelessWidget {
  const SingleBattle({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Single Battle"),
        backgroundColor: Colors.yellow,
      ),
      body: const SingleChildScrollView(
        child: MySingleBattlePage(),
      ),
    );
  }
}

class MySingleBattlePage extends StatefulWidget {
  const MySingleBattlePage({super.key});

  @override
  State<MySingleBattlePage> createState() => _MySingleBattlePageState();
}

class _MySingleBattlePageState extends State<MySingleBattlePage> {
  PokemonApi api = PokemonApi();
  int first = 0;
  int second = 1;
  String battleResults = "Start a battle to see the results!";
  TextEditingController setSeedController = TextEditingController();

  @override
  void initState() {
    super.initState();
    setSeedController.text = "0";
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Single Battle"),
        backgroundColor: Colors.yellow,
      ),
      body: SingleChildScrollView(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const Text("First Pokemon: "),
                DropdownButton<int>(
                  hint: const Text("Select a Pokemon"),
                  value: first,
                  onChanged: (int? value) {
                    if (value == null) {
                      return;
                    }
                    setState(() {
                      first = value;
                    });
                  },
                  items: List.generate(
                    Pokemon.catalogue.length,
                    (int index) {
                      Pokemon p = Pokemon.catalogue[index];
                      return DropdownMenuItem<int>(
                        value: index,
                        child: Text(p.nickname),
                      );
                    },
                  ),
                ),
              ],
            ),
            const SizedBox(height: 20),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const Text("Second Pokemon: "),
                DropdownButton<int>(
                  hint: const Text("Select a Pokemon"),
                  value: second,
                  onChanged: (int? value) {
                    if (value == null) {
                      return;
                    }
                    setState(() {
                      second = value;
                    });
                  },
                  items: List.generate(
                    Pokemon.catalogue.length,
                    (int index) {
                      Pokemon p = Pokemon.catalogue[index];
                      return DropdownMenuItem<int>(
                        value: index,
                        child: Text(p.nickname),
                      );
                    },
                  ),
                ),
              ],
            ),
            const SizedBox(height: 20),
            Row(
              mainAxisSize: MainAxisSize.min,
              children: [
                const Text("Battle Seed: "),
                SizedBox(
                  width: 50,
                  child: TextField(
                    controller: setSeedController,
                    textAlign: TextAlign.center,
                    keyboardType: TextInputType.number,
                    decoration: const InputDecoration(
                      border: OutlineInputBorder(),
                      contentPadding:
                          EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                    ),
                  ),
                ),
              ],
            ),
            Padding(
              padding: const EdgeInsets.symmetric(vertical: 20.0),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  ElevatedButton(
                    onPressed: () async {
                      Battle battle = Battle(
                        first: Pokemon.catalogue[first],
                        second: Pokemon.catalogue[second],
                        seed: int.tryParse(setSeedController.text),
                      );
                      battleResults =
                          await PokemonApi().simulateSingleBattle(battle);
                      setState(() {});
                    },
                    style: ButtonStyle(
                      backgroundColor:
                          WidgetStateProperty.all<Color>(Colors.yellow),
                    ),
                    child: const Text("Start Battle"),
                  ),
                ],
              ),
            ),
            const SizedBox(height: 20),
            const Text(
              "Results",
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
            Text(battleResults),
          ],
        ),
      ),
    );
  }
}
