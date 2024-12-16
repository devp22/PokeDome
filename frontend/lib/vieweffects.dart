import 'package:flutter/material.dart';
import 'package:frontend/pokemon_models/effects/effect.dart';

/// This screen is used to view [Effect]s.
class ViewEffects extends StatelessWidget {
  const ViewEffects({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('View Effects'),
        backgroundColor: Colors.yellow,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: SingleChildScrollView(
          child: DataTable(
            border: TableBorder.all(
              color: Colors.black,
              width: 1,
            ),
            columns: const [
              DataColumn(label: Text('Effect Description')),
            ],
            rows: List.generate(
              Effect.catalogue.length,
              (int index) {
                Effect effect = Effect.catalogue[index];
                return DataRow(
                  cells: [
                    DataCell(Text(effect.getDescription())),
                  ],
                );
              },
            ),
          ),
        ),
      ),
    );
  }
}
