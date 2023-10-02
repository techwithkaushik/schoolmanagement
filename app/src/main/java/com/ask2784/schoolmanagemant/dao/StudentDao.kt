//package com.ask2784.schoolmanagemant.dao;
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//import com.ask2784.schoolmanagemant.models.Student;
//import java.util.List;
//
//
//@Dao
//public interface StudentDao {
//    
//    @Insert
//    long insert(Student student);
//    
//    @Update
//    void update(Student student);
//    
//    @Delete
//    void delete(Student student);
//    
//    @Query("DELETE FROM student_details")
//    void deleteAllStudent();
//    
//    @Query("SELECT * FROM student_details WHERE id = :id")
//    Student getStudentById(long id);
//    
//    @Query("SELECT * FROM student_details ORDER BY name, father_name, class_name ASC")
//    LiveData<List<Student>> getAllStudent();
//}
//

package com.ask2784.schoolmanagemant.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ask2784.schoolmanagemant.models.Student

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
