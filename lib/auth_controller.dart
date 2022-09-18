import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:schoolmanagement/pages/admin_page.dart';
import 'package:schoolmanagement/widgets.dart';

import 'pages/login_page.dart';
import 'pages/parents_page.dart';
import 'pages/student_page.dart';
import 'pages/teacher_page.dart';

class AuthController extends GetxController {
  static AuthController instance = Get.find();

  late Rx<User?> _user;
  FirebaseAuth auth = FirebaseAuth.instance;

  FirebaseFirestore db = FirebaseFirestore.instance;

  @override
  void onReady() {
    super.onReady();
    _user = Rx<User?>(auth.currentUser);
    _user.bindStream(auth.userChanges());
    ever(_user, _initialScreen);
  }

  _initialScreen(User? user) async {
    if (user == null) {
      Get.offAll(() => const LoginPage());
    } else {
      List data = await db
          .collection("Users")
          .where("uId", isEqualTo: user.uid)
          .get()
          .then((value) => value.docs);
      if (data.isNotEmpty) {
        String role = data.first["role"];
        if (role == "admin") {
          Get.offAll(AdminPage(uId: user.uid, email: user.email!, role: role));
        } else if (role == "teacher") {
          Get.offAll(
              TeacherPage(uId: user.uid, email: user.email!, role: role));
        } else if (role == "parents") {
          Get.offAll(
              ParentsPage(uId: user.uid, email: user.email!, role: role));
        } else if (role == "student") {
          Get.offAll(
              StudentPage(uId: user.uid, email: user.email!, role: role));
        } else {
          CustomWidgets.showSnackBar(
              title: "User Error",
              message: "Role Not Found",
              backGroundColor: Colors.red);
        }
      } else {
        CustomWidgets.showSnackBar(
            title: "User Error",
            message: "User Not Found",
            backGroundColor: Colors.red);
      }
    }
  }

  void loginEmailPasss(String email, pass) async {
    try {
      await auth.signInWithEmailAndPassword(
        email: email,
        password: pass,
      );
    } on FirebaseAuthException catch (e) {
      if (e.code == 'user-not-found') {
        CustomWidgets.showSnackBar(
            title: "Login Error",
            message: "Email Not Found",
            backGroundColor: Colors.red);
      } else if (e.code == 'wrong-password') {
        CustomWidgets.showSnackBar(
            title: "Login Error",
            message: "Incorrect Password",
            backGroundColor: Colors.red);
      } else {
        CustomWidgets.showSnackBar(
            title: "Login Error",
            message: e.message.toString(),
            backGroundColor: Colors.red);
      }
    }
  }

  void logOut() async {
    await auth.signOut();
  }
}
