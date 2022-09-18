import 'package:flutter/material.dart';
import 'package:schoolmanagement/auth_controller.dart';

class AdminPage extends StatefulWidget {
  const AdminPage({
    super.key,
    required this.uId,
    required this.email,
    required this.role,
  });

  final String uId;
  final String email;
  final String role;

  @override
  State<AdminPage> createState() => _AdminPageState();
}

class _AdminPageState extends State<AdminPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Admin Page"),
        actions: [
          Material(
            type: MaterialType.transparency,
            clipBehavior: Clip.hardEdge,
            shape: const CircleBorder(),
            child: InkWell(
              onTap: () {
                AuthController.instance.logOut();
              },
              child: const Padding(
                padding: EdgeInsets.all(8.0),
                child: Icon(
                  Icons.exit_to_app_outlined,
                ),
              ),
            ),
          ),
        ],
      ),
      body: Center(
        child: Text(
          "${widget.role} Email\n${widget.email}",
        ),
      ),
    );
  }
}
