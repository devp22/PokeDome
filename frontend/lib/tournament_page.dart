import 'package:flutter/material.dart';
import 'package:frontend/info_popup.dart';
import 'package:frontend/main.dart';
import 'package:frontend/pokemon_api/pokemon_api.dart';
import 'package:frontend/pokemon_models/pokemon.dart';
import 'package:frontend/pokemon_models/tournament.dart';

/// This screen is used to simulate [Tournament]s.
class TournamentPage extends StatelessWidget {
  const TournamentPage({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Tournament',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.yellow),
        useMaterial3: true,
      ),
      home: const MyTournamentPage(title: 'Tournament'),
    );
  }
}

class MyTournamentPage extends StatefulWidget {
  const MyTournamentPage({super.key, required this.title});
  final String title;

  @override
  State<MyTournamentPage> createState() => _MyTournamentPageState();
}

class _MyTournamentPageState extends State<MyTournamentPage> {
  List<int> pokemonIndicies = [0, 0, 0, 0];
  int tournamentTypeIndex = 0;
  TextEditingController setSeedController = TextEditingController();
  String tournamentResults = "Start a tournament to see the results!";

  @override
  void initState() {
    super.initState();
    setSeedController.text = "0";
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          icon: const Icon(Icons.arrow_back, color: Colors.black),
          onPressed: () => Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => const MyApp()),
          ),
        ),
        title: const Text(
          "Tournament",
          style: TextStyle(color: Colors.black),
        ),
        backgroundColor: Colors.yellow,
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Card(
                elevation: 4,
                margin: const EdgeInsets.only(bottom: 16.0),
                child: Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      const Text(
                        "Select Tournament Type:",
                        style: TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      const SizedBox(height: 10),
                      DropdownButton<int>(
                        value: tournamentTypeIndex,
                        hint: const Text("Choose Tournament Type"),
                        items: List.generate(
                          Tournament.types.length,
                          (int index) {
                            return DropdownMenuItem<int>(
                              value: index,
                              child: Text(
                                Tournament.types[index],
                              ),
                            );
                          },
                        ),
                        onChanged: (int? newIndex) {
                          if (newIndex == null) {
                            return;
                          }
                          tournamentTypeIndex = newIndex;
                          setState(() {});
                        },
                        isExpanded: true,
                      ),
                    ],
                  ),
                ),
              ),
              Card(
                elevation: 4,
                margin: const EdgeInsets.only(bottom: 16.0),
                child: Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.start,
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
                            contentPadding: EdgeInsets.symmetric(
                                horizontal: 8, vertical: 4),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              const SizedBox(height: 10),
              Card(
                elevation: 4,
                child: Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      const Text(
                        "Select Pokémons:",
                        style: TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      const SizedBox(height: 10),
                      ListView.builder(
                        itemCount: pokemonIndicies.length,
                        shrinkWrap: true,
                        physics: const NeverScrollableScrollPhysics(),
                        itemBuilder: (context, entryIndex) {
                          return Padding(
                            padding: const EdgeInsets.symmetric(vertical: 8.0),
                            child: Row(
                              children: [
                                Expanded(
                                  child: DropdownButton<int>(
                                    value: pokemonIndicies[entryIndex],
                                    hint: const Text("Choose Pokémon"),
                                    items: List.generate(
                                      Pokemon.catalogue.length,
                                      (int pokemonIndex) {
                                        return DropdownMenuItem(
                                          value: pokemonIndex,
                                          child: Text(
                                            Pokemon.catalogue[pokemonIndex]
                                                .nickname,
                                          ),
                                        );
                                      },
                                    ),
                                    onChanged: (int? newIndex) {
                                      if (newIndex == null) {
                                        return;
                                      }
                                      pokemonIndicies[entryIndex] = newIndex;
                                      setState(() {});
                                    },
                                    isExpanded: true,
                                  ),
                                ),
                                IconButton(
                                  icon: const Icon(
                                    Icons.delete,
                                    color: Colors.red,
                                  ),
                                  onPressed: () {
                                    if (pokemonIndicies.length <= 4) {
                                      showInfoPopup(
                                        context,
                                        "Invalid Operation",
                                        "Cannot have less than 4 Pokemon in a Tournament",
                                      );
                                      return;
                                    }
                                    pokemonIndicies.removeAt(entryIndex);
                                    setState(() {});
                                  },
                                ),
                              ],
                            ),
                          );
                        },
                      ),
                      const SizedBox(height: 10),
                      Align(
                        alignment: Alignment.center,
                        child: ElevatedButton.icon(
                          onPressed: () {
                            pokemonIndicies.add(0);
                            setState(() {});
                          },
                          style: ElevatedButton.styleFrom(
                            backgroundColor: Colors.yellow,
                          ),
                          icon: const Icon(Icons.add, color: Colors.black),
                          label: const Text(
                            "Add Pokémon",
                            style: TextStyle(color: Colors.black),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              Padding(
                padding: const EdgeInsets.symmetric(vertical: 20.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    ElevatedButton(
                      onPressed: () async {
                        Tournament tournament = Tournament(
                          pokemons: List.generate(
                            pokemonIndicies.length,
                            (int index) {
                              return Pokemon.catalogue[pokemonIndicies[index]];
                            },
                          ),
                          type: Tournament.types[tournamentTypeIndex],
                          seed: int.tryParse(setSeedController.text),
                        );
                        tournamentResults =
                            await PokemonApi().simulateTournament(tournament);
                        setState(() {});
                      },
                      style: ButtonStyle(
                        backgroundColor:
                            WidgetStateProperty.all<Color>(Colors.yellow),
                      ),
                      child: const Text("Start Tournament"),
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
              Text(tournamentResults),
            ],
          ),
        ),
      ),
    );
  }
}
