package com.ask2784.schoolmanagement.models;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "student_result")
data class StudentResult(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "student_id") var studentId: Long = 0,
    @ColumnInfo(name = "subject_name") var subjectName: String = "",
    @ColumnInfo(name = "max_marks") var maxMarks: Int = 0,
    @ColumnInfo(name = "obtain_marks") var obtainMarks: Int = 0
    ) : Serializable {
    
    constructor(studentId: Long, subjectName: String, maxMarks: Int, obtainMarks: Int) : this() {
        this.studentId = studentId
        this.subjectName = subjectName
        this.maxMarks = maxMarks
        this.obtainMarks = obtainMarks
    }
}
