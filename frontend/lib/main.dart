import 'package:flutter/material.dart';
import 'package:frontend/manageskills.dart';
import 'package:frontend/pokemon_api/pokemon_api.dart';
import 'package:frontend/singlebattle.dart';
import 'package:frontend/tournament_page.dart';
import 'package:frontend/vieweffects.dart';
import 'package:frontend/viewpokemon.dart';

/// Main method for the frontend. Grabs all [Pokemon] from the backend API
/// and then runs the app.
void main() {
  PokemonApi().init();
  runApp(
    const MyApp(),
  );
}

/// The main screen for the GUI. Displays the options available to the user.
/// The options are to manage pokemon, manage skills, view effects, simulate
/// a single battle, or simulate a tournament. Each option brings the user
/// to a separate screen.
class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.yellow),
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'Welcome to Poke-Dome'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => const ViewPokemon()),
                );
              },
              style: const ButtonStyle(
                backgroundColor: WidgetStatePropertyAll<Color>(Colors.yellow),
              ),
              child: const Text('Manage Pokemon'),
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => const ManageSkills()),
                );
              },
              style: const ButtonStyle(
                backgroundColor: WidgetStatePropertyAll<Color>(Colors.yellow),
              ),
              child: const Text('Manage Skills'),
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => const ViewEffects()),
                );
              },
              style: const ButtonStyle(
                backgroundColor: WidgetStatePropertyAll<Color>(Colors.yellow),
              ),
              child: const Text('View Effects'),
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => const MySingleBattlePage()),
                );
              },
              style: const ButtonStyle(
                backgroundColor: WidgetStatePropertyAll<Color>(Colors.yellow),
              ),
              child: const Text('Single Battle'),
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => const TournamentPage()),
                );
              },
              style: const ButtonStyle(
                backgroundColor: WidgetStatePropertyAll<Color>(Colors.yellow),
              ),
              child: const Text('Tournament'),
            ),
          ],
        ),
      ),
    );
  }
}
