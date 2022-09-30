import 'package:flutter/material.dart';
import 'package:get/get.dart';

class CustomWidgets {
  static void showSnackBar({
    required String title,
    required String message,
  }) {
    Get.snackbar(
      title,
      message,
      colorText: Colors.black,
      backgroundColor: Colors.purple.shade50,
      snackPosition: SnackPosition.BOTTOM,
    );
  }
}
