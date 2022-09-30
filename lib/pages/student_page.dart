import 'package:flutter/material.dart';
import 'package:schoolmanagement/controllers/auth_controller.dart';

class StudentPage extends StatefulWidget {
  const StudentPage({
    Key? key,
    required this.uId,
    required this.email,
    required this.role,
  }) : super(key: key);

  final String uId;
  final String email;
  final String role;
  @override
  State<StudentPage> createState() => _StudentPageState();
}

class _StudentPageState extends State<StudentPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Student Page"),
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
                child: Icon(Icons.exit_to_app_outlined),
              ),
            ),
          ),
        ],
      ),
      body: Center(
        child: Text("Student Page\nRole: ${widget.role}\n${widget.email}"),
      ),
    );
  }
}
