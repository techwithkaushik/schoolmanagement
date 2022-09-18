import 'package:flutter/material.dart';
import 'package:get/get.dart';

class CustomWidgets {
  static void showSnackBar({
    required String title,
    required String message,
    required Color backGroundColor,
  }) {
    Get.snackbar(
      title,
      message,
      colorText: Colors.white,
      backgroundColor: backGroundColor,
      snackPosition: SnackPosition.BOTTOM,
    );
  }
}
