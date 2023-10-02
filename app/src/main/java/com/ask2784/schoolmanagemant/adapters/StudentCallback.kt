package com.ask2784.schoolmanagemant.adapters

import androidx.recyclerview.widget.DiffUtil
import com.ask2784.schoolmanagemant.models.Student

object StudentCallback {
    
    val itemCallback: DiffUtil.ItemCallback<Student> get()  = object : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldStudent: Student, newStudent: Student) =
            oldStudent.id == newStudent.id

        override fun areContentsTheSame(oldStudent: Student, newStudent: Student) =
            oldStudent == newStudent
    }

    val newItemCallback = object : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldStudent: Student, newStudent: Student) =
            oldStudent.name == newStudent.name

        override fun areContentsTheSame(oldStudent: Student, newStudent: Student) =
            oldStudent.name == newStudent.name &&
                    oldStudent.className == newStudent.className &&
                    oldStudent.fatherName == newStudent.fatherName
    }
}