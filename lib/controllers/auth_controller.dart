import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:get/get.dart';
import 'package:schoolmanagement/pages/admin_page.dart';
import 'package:schoolmanagement/widgets.dart';

import '../pages/login_page.dart';
import '../pages/parents_page.dart';
import '../pages/student_page.dart';
import '../pages/teacher_page.dart';

class AuthController extends GetxController {
  static AuthController instance = Get.find();

  late Rx<User?> _user;
  final FirebaseAuth _auth = FirebaseAuth.instance;

  final FirebaseFirestore _db = FirebaseFirestore.instance;

  @override
  void onReady() {
    super.onReady();
    _user = Rx<User?>(_auth.currentUser);
    _user.bindStream(_auth.userChanges());
    ever(_user, _initialScreen);
  }

  _initialScreen(User? user) async {
    if (user == null) {
      Get.offAll(() => const LoginPage());
    } else {
      List data = await _db
          .collection("Users")
          .where("uId", isEqualTo: user.uid)
          .get()
          .then((value) => value.docs.obs);
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
          );
        }
      } else {
        CustomWidgets.showSnackBar(
          title: "User Error",
          message: "User Not Found",
        );
      }
    }
  }

  void loginEmailPasss(String email, pass) async {
    try {
      await _auth.signInWithEmailAndPassword(
        email: email,
        password: pass,
      );
    } on FirebaseAuthException catch (e) {
      if (e.code == 'user-not-found') {
        CustomWidgets.showSnackBar(
          title: "Login Error",
          message: "Email Not Found",
        );
      } else if (e.code == 'wrong-password') {
        CustomWidgets.showSnackBar(
          title: "Login Error",
          message: "Incorrect Password",
        );
      } else {
        CustomWidgets.showSnackBar(
          title: "Login Error",
          message: e.message.toString(),
        );
      }
    }
  }

  void logOut() async {
    await _auth.signOut();
  }
}
