package com.ask2784.schoolmanagement.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ask2784.schoolmanagement.models.Student

@Dao
interface StudentDao {

    @Insert
    fun insert(student: Student): Long

    @Update
    fun update(student: Student)

    @Delete
    fun delete(student: Student)

    @Query("DELETE FROM student_details")
    fun deleteAllStudent()

    @Query("SELECT * FROM student_details WHERE id = :id")
    fun getStudentById(id: Long): Student?

    @Query("SELECT * FROM student_details ORDER BY name, father_name, class_name ASC")
    fun getAllStudent(): LiveData<List<Student>>
}
