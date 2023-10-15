package com.ask2784.schoolmanagement.dao;

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ask2784.schoolmanagement.models.StudentResult

@Dao
interface StudentResultDao {

    @Insert
    fun insert(result: StudentResult): Long

    @Update
    fun update(result: StudentResult)

    @Delete
    fun delete(result: StudentResult)

    @Query("SELECT * FROM student_result WHERE student_id = :studentId ORDER BY subject_name")
    fun getResultByStudentId(studentId: Long): LiveData<List<StudentResult>>
}