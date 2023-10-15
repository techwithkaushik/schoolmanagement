package com.ask2784.schoolmanagement.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.ask2784.schoolmanagement.dao.StudentDao
import com.ask2784.schoolmanagement.models.Student

class StudentRepo(application: Application) {
    private val studentDao: StudentDao by lazy { DatabaseClients.getDatabase(application).studentDao() }

    val allStudent: LiveData<List<Student>> by lazy { studentDao.getAllStudent() }

    fun insert(student: Student) = studentDao.insert(student)
    fun getStudentById(id: Long) = studentDao.getStudentById(id)
    fun update(student: Student) = studentDao.update(student)
    fun delete(student: Student) = studentDao.delete(student)
    fun deleteAllStudent() = studentDao.deleteAllStudent()
}