import 'package:flutter/material.dart';

/// This function pops up a dialog with the given [title] and [message].
/// There is a single button to dismiss the dialog reading "OK".
Future<void> showInfoPopup(
  BuildContext context,
  String title,
  String message,
) async {
  return showDialog(
    context: context,
    builder: (BuildContext context) {
      return AlertDialog(
        title: Text(title),
        content: Text(message),
        actions: [
          TextButton(
            onPressed: () {
              Navigator.pop(context);
            },
            child: const Text("OK"),
          ),
        ],
      );
    },
  );
}
