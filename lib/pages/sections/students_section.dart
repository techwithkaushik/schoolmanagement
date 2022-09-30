import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../../widgets.dart';

class StudentSection extends StatefulWidget {
  final String secName;

  const StudentSection({super.key, required this.secName});

  @override
  State<StudentSection> createState() => _StudentSectionState();
}

class _StudentSectionState extends State<StudentSection> {
  List items = [
    "New Admission",
    "Students List",
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.secName),
      ),
      body: ListView.builder(
        itemCount: items.length,
        itemBuilder: (context, index) {
          return Container(
            margin: const EdgeInsets.all(20),
            padding: const EdgeInsets.all(20),
            decoration: BoxDecoration(
              color: Colors.purple.shade50,
              borderRadius: BorderRadius.circular(20),
            ),
            child: InkWell(
              borderRadius: BorderRadius.circular(20),
              onTap: () {
                CustomWidgets.showSnackBar(
                  title: "Clicked ",
                  message: items[index],
                );
                if (index == 0) {
                  Get.to(
                    NewAdmission(newAdd: items[index]),
                  );
                } else if (index == 1) {}
              },
              child: Center(
                child: Text(
                  items[index],
                ),
              ),
            ),
          );
        },
      ),
    );
  }
}

class NewAdmission extends StatefulWidget {
  final String newAdd;

  const NewAdmission({super.key, required this.newAdd});

  @override
  State<NewAdmission> createState() => _NewAdmissionState();
}

class _NewAdmissionState extends State<NewAdmission> {
  final formKey = GlobalKey<FormState>();

  var nameCont = TextEditingController();
  var fNameCont = TextEditingController();
  var mNameCont = TextEditingController();
  var classCont = TextEditingController();
  var srnCont = TextEditingController();
  var addYCont = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      appBar: AppBar(
        title: Text(widget.newAdd),
      ),
      body: Padding(
        padding: const EdgeInsets.all(20),
        child: Form(
          key: formKey,
          autovalidateMode: AutovalidateMode.onUserInteraction,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              // name
              TextFormField(
                controller: nameCont,
                maxLines: 1,
                decoration: const InputDecoration(
                  label: Text("Student's Name"),
                  hintText: "Student's Name",
                ),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return "Please Enter Student's Name";
                  } else {
                    return null;
                  }
                },
              ),
              const SizedBox(height: 20),
              // father name
              TextFormField(
                controller: fNameCont,
                maxLines: 1,
                decoration: const InputDecoration(
                  label: Text("Father's Name"),
                  hintText: "Father's Name",
                ),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return "Please Enter Father's Name";
                  } else {
                    return null;
                  }
                },
              ),
              const SizedBox(height: 20),
              // mother name
              TextFormField(
                controller: mNameCont,
                maxLines: 1,
                decoration: const InputDecoration(
                  label: Text("Mother's Name"),
                  hintText: "Mother's Name",
                ),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return "Please Enter Mother's Name";
                  } else {
                    return null;
                  }
                },
              ),
              const SizedBox(height: 20),
              // class
              TextFormField(
                controller: classCont,
                maxLines: 1,
                decoration: const InputDecoration(
                  label: Text("Class"),
                  hintText: "Class",
                ),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return "Please Enter Class";
                  } else {
                    return null;
                  }
                },
              ),
              const SizedBox(height: 20),
              // s r num
              TextFormField(
                controller: srnCont,
                maxLines: 1,
                decoration: const InputDecoration(
                  label: Text("S.R. Number"),
                  hintText: "S.R. Number",
                ),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return "Please Enter S.R. Number";
                  } else {
                    return null;
                  }
                },
              ),
              const SizedBox(height: 20),
              // admission year
              TextFormField(
                controller: addYCont,
                maxLines: 1,
                decoration: const InputDecoration(
                  label: Text("Admission Year"),
                  hintText: "Admission Year",
                ),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return "Please Enter Admission Year";
                  } else {
                    return null;
                  }
                },
              ),
              const SizedBox(height: 30),
              ElevatedButton(
                onPressed: () {
                  if (formKey.currentState!.validate()) {
                    CustomWidgets.showSnackBar(
                        title: "Student Added", message: nameCont.text);
                  }
                },
                child: const Text("Submit"),
              )
            ],
          ),
        ),
      ),
    );
  }
}
