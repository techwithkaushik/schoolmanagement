import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:schoolmanagement/controllers/auth_controller.dart';
import 'package:schoolmanagement/widgets.dart';

import 'sections/students_section.dart';

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
  List sectionsName = [
    "Teacher's Section",
    "Student's Section",
    "Fees Section",
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Admin Page"),
        actions: [
          TextButton(
            onPressed: () => AuthController.instance.logOut(),
            child: const Text("Logout"),
          ),
        ],
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          const Text("Welcome to School Management"),
          const SizedBox(height: 40),
          Expanded(
            child: GridView.builder(
                padding: const EdgeInsets.only(left: 20, right: 20),
                itemCount: sectionsName.length,
                gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                  crossAxisCount: 2,
                  mainAxisSpacing: 20,
                  crossAxisSpacing: 20,
                  childAspectRatio: 1.5,
                ),
                itemBuilder: (context, index) {
                  return InkWell(
                    borderRadius: BorderRadius.circular(20),
                    onTap: () {
                      CustomWidgets.showSnackBar(
                        title: "Clicked",
                        message: sectionsName[index],
                      );
                    },
                    child: Container(
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(20),
                        color: Colors.pink.shade50,
                      ),
                      child: Center(child: Text(sectionsName[index])),
                    ),
                  );
                }),
            // ListView.builder(
            //   shrinkWrap: true,
            //   itemCount: sectionsName.length,
            //   itemBuilder: (context, index) {
            //     return Container(
            //       margin: const EdgeInsets.all(20),
            //       padding: const EdgeInsets.all(20),
            //       decoration: BoxDecoration(
            //         color: Colors.purple.shade50,
            //         borderRadius: BorderRadius.circular(20),
            //       ),
            //       child: InkWell(
            //         borderRadius: BorderRadius.circular(20),
            //         onTap: () {
            //           CustomWidgets.showSnackBar(
            //             title: "Clicked ",
            //             message: sectionsName[index],
            //           );
            //           if (index == 0) {
            //           } else if (index == 1) {
            //             Get.to(
            //               StudentSection(
            //                 secName: sectionsName[index],
            //               ),
            //             );
            //           } else if (index == 2) {}
            //         },
            //         child: Center(
            //           child: Text(
            //             sectionsName[index],
            //           ),
            //         ),
            //       ),
            //     );
            //   },
            // ),
          ),
        ],
      ),
    );
  }
}
