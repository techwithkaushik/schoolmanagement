import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:schoolmanagement/auth_controller.dart';

import '../constants.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  var emailCont = TextEditingController();
  var passCont = TextEditingController();

  bool obscureText = true;

  final formKey = GlobalKey<FormState>();

  @override
  void dispose() {
    super.dispose();
    emailCont.dispose();
    passCont.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      body: Container(
        padding: const EdgeInsets.all(20),
        child: Form(
          key: formKey,
          autovalidateMode: AutovalidateMode.onUserInteraction,
          child: OrientationBuilder(
              builder: (context, orientation) =>
                  orientation == Orientation.portrait
                      ? portraitScreen()
                      : landScapeScreen()),
        ),
      ),
    );
  }

  Widget schoolLogo() => Container(
        padding: const EdgeInsets.only(
          top: 40,
          bottom: 30,
          left: 50,
          right: 50,
        ),
        child: ClipRRect(
          child: Image.asset(
            "assets/school_logo.png",
          ),
        ),
      );

  Widget schoolWelcome() => const Text(
        "Welcome to school management",
        style: TextStyle(fontSize: 18),
      );

  Widget emailField() => TextFormField(
        controller: emailCont,
        maxLines: 1,
        textInputAction: TextInputAction.next,
        decoration: const InputDecoration(
          label: Text("Email"),
          hintText: "Enter Email",
          suffixIcon: Icon(
            Icons.email_outlined,
            color: Colors.green,
          ),
        ),
        validator: (value) {
          RegExp regExp = RegExp(emailPattern);
          if (value == null || value.isEmpty) {
            return "Please enter email!";
          } else if (!regExp.hasMatch(value)) {
            return "Please enter valid email!";
          } else {
            return null;
          }
        },
      );

  Widget passField() => TextFormField(
        controller: passCont,
        maxLines: 1,
        textInputAction: TextInputAction.done,
        obscureText: obscureText,
        decoration: InputDecoration(
          label: const Text("Password"),
          hintText: "Enter Password",
          suffixIcon: Material(
            type: MaterialType.transparency,
            clipBehavior: Clip.hardEdge,
            shape: const CircleBorder(),
            child: InkWell(
              onTap: () {
                setState(() {
                  obscureText = !obscureText;
                });
              },
              child: obscureText
                  ? const Icon(
                      Icons.visibility_off_outlined,
                      color: Colors.green,
                    )
                  : const Icon(
                      Icons.visibility_outlined,
                      color: Colors.red,
                    ),
            ),
          ),
        ),
        validator: (value) {
          if (value!.isEmpty) {
            return "Please enter password";
          } else if (value.length < 6) {
            return "Please enter more then 5 characters";
          } else {
            return null;
          }
        },
      );

  Widget clickButton() => ElevatedButton(
        onPressed: () {
          if (formKey.currentState!.validate()) {
            AuthController.instance.loginEmailPasss(
              emailCont.text.trim(),
              passCont.text,
            );
          }
        },
        child: const Text("Login"),
      );

  Widget portraitScreen() => Column(
        mainAxisAlignment: MainAxisAlignment.start,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          schoolLogo(),
          schoolWelcome(),
          const SizedBox(height: 20),
          emailField(),
          const SizedBox(height: 30),
          passField(),
          const SizedBox(height: 40),
          clickButton(),
        ],
      );

  Widget landScapeScreen() => Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          schoolLogo(),
          Flexible(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                schoolWelcome(),
                const SizedBox(height: 10),
                emailField(),
                const SizedBox(height: 20),
                passField(),
                const SizedBox(height: 25),
                clickButton(),
              ],
            ),
          ),
        ],
      );
}
