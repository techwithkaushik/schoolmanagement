import 'package:flutter/material.dart';

class TeacherPage extends StatefulWidget {
  const TeacherPage({
    Key? key,
    required this.uId,
    required this.email,
    required this.role,
  }) : super(key: key);

  final String uId;
  final String email;
  final String role;
  @override
  State<TeacherPage> createState() => _TeacherPageState();
}

class _TeacherPageState extends State<TeacherPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Text("Teacher Page\nRole: ${widget.role}"),
      ),
    );
  }
}
