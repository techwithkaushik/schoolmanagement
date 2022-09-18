import 'package:flutter/material.dart';

class ParentsPage extends StatefulWidget {
  const ParentsPage({
    Key? key,
    required this.uId,
    required this.email,
    required this.role,
  }) : super(key: key);

  final String uId;
  final String email;
  final String role;
  @override
  State<ParentsPage> createState() => _ParentsPageState();
}

class _ParentsPageState extends State<ParentsPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Text("Parents Page\nRole: ${widget.role}"),
      ),
    );
  }
}
