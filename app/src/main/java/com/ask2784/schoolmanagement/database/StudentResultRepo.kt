package com.ask2784.schoolmanagement.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.ask2784.schoolmanagement.dao.StudentResultDao
import com.ask2784.schoolmanagement.models.StudentResult

class StudentResultRepo(application: Application) {
    private val studentResultDao: StudentResultDao by lazy { DatabaseClients.getDatabase(application).studentResultDao() }

    fun getResultByStudentId(studentId: Long): LiveData<List<StudentResult>> { return studentResultDao.getResultByStudentId(studentId) }

    fun insert(result: StudentResult) = studentResultDao.insert(result)
    fun update(result: StudentResult) = studentResultDao.update(result)
    fun delete(result: StudentResult) = studentResultDao.delete(result)
}