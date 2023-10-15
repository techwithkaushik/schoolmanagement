package com.ask2784.schoolmanagement.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "student_details")
data class Student(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "father_name") var fatherName: String = "",
    @ColumnInfo(name = "class_name") var className: String = ""
    ) : Serializable {
    
    constructor(name: String, fatherName: String, className: String) : this() {
        this.name = name
        this.fatherName = fatherName
        this.className = className
    }
}