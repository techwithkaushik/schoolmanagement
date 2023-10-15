package com.ask2784.schoolmanagement.adapters

import androidx.recyclerview.widget.DiffUtil
import com.ask2784.schoolmanagement.models.Student

object StudentCallback {
    
    val itemCallback = object : DiffUtil.ItemCallback<Student>() {
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