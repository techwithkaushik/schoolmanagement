import 'package:get/get.dart';

import '../models/models.dart';

class StudentController extends GetxController {
  static StudentController instance = Get.find();
  late Rx<StudentModel> _student;
}
