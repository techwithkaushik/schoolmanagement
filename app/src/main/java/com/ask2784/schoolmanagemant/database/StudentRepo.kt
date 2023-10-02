//package com.ask2784.schoolmanagemant.database;
//import android.app.Application;
//import androidx.lifecycle.LiveData;
//import com.ask2784.schoolmanagemant.dao.StudentDao;
//import com.ask2784.schoolmanagemant.models.Student;
//import java.util.List;
//
//public class StudentRepo {
//    DatabaseClients db;
//    StudentDao studentDao;
//    private LiveData<List<Student>> allStudent;
//    
//    public StudentRepo(Application app) {
//        db = DatabaseClients.getDatabase(app);
//        studentDao = db.studentDao();
//        allStudent = studentDao.getAllStudent();
//    }
//    
//    public long insert(Student student) {
//        return studentDao.insert(student);
//    }
//    
//    public Student getStudentById(long id) {
//    	return studentDao.getStudentById(id);
//    }
//    
//    public void update(Student student) {
//    	studentDao.update(student);
//    }
//    
//    public void delete(Student student) {
//    	studentDao.delete(student);
//    }
//    
//    public void deleteAllStudent() {
//    	studentDao.deleteAllStudent();
//    }
//    
//    public LiveData<List<Student>> getAllStudent() {
//    	return allStudent;
//    }
//}
//

package com.ask2784.schoolmanagemant.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.ask2784.schoolmanagemant.dao.StudentDao
import com.ask2784.schoolmanagemant.models.Student

class StudentRepo(application: Application) {
    private val studentDao: StudentDao by lazy { DatabaseClients.getDatabase(application).studentDao() }

    val allStudent: LiveData<List<Student>> by lazy { studentDao.getAllStudent() }

    fun insert(student: Student) = studentDao.insert(student)
    fun getStudentById(id: Long) = studentDao.getStudentById(id)
    fun update(student: Student) = studentDao.update(student)
    fun delete(student: Student) = studentDao.delete(student)
    fun deleteAllStudent() = studentDao.deleteAllStudent()
}