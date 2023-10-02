//package com.ask2784.schoolmanagemant.models;
//import androidx.recyclerview.widget.DiffUtil;
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.Ignore;
//import androidx.room.PrimaryKey;
//import java.io.Serializable;
//import java.util.Comparator;
//
//
//@Entity(tableName = "student_details")
//public class Student implements Serializable{
//    
//    @PrimaryKey(autoGenerate = true)
//    private long id;
//    
//    @ColumnInfo(name = "name")
//    private String name;
//    
//    @ColumnInfo(name = "father_name")
//    private String fatherName;
//    
//    @ColumnInfo(name = "class_name")
//    private String className;
//    
//    @Ignore
//    public Student() {
//    }
//    
//    public Student(String name, String fatherName, String className) {
//        this.name = name;
//        this.fatherName = fatherName;
//        this.className = className;
//    }
//    
//    public long getId() {
//        return this.id;
//    }
//    public void setId(long id) {
//        this.id = id;
//    }
//    public String getName() {
//        return this.name;
//    }
//    public void setName(String name) {
//        this.name = name;
//    }
//    public String getFatherName() {
//        return this.fatherName;
//    }
//    public void setFatherName(String fatherName) {
//        this.fatherName = fatherName;
//    }
//    public String getClassName() {
//        return this.className;
//    }
//    public void setClassName(String className) {
//        this.className = className;
//    }
//    
//    @Override
//    public String toString() {
//        return "Student[id=" + id + ", name=" + name + ", fatherName=" + fatherName + ", className=" + className + "]";
//    }
//    
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Student student = (Student) o;
//        return id == student.id &&
//            name.equals(student.name) &&
//            className.equals(student.className) &&
//            fatherName.equals(student.fatherName);
//    }
//    
//    public static DiffUtil.ItemCallback<Student> itemCallback = new DiffUtil.ItemCallback<Student>(){
//        
//        @Override
//        public boolean areItemsTheSame(Student oldStudent, Student newStudent) {
//            return oldStudent.id == newStudent.id;
//        }
//        
//
//        @Override
//        public boolean areContentsTheSame(Student oldStudent, Student newStudent) {
//            return oldStudent.equals(newStudent);
//        }
//    };
//    
//    public static DiffUtil.ItemCallback<Student> newItemCallback = new DiffUtil.ItemCallback<Student>(){
//        
//        @Override
//        public boolean areItemsTheSame(Student oldStudent, Student newStudent) {
//            return oldStudent.name == newStudent.name;
//        }
//        
//
//        @Override
//        public boolean areContentsTheSame(Student oldStudent, Student newStudent) {
//            return oldStudent.name.equals(newStudent.name) &&
//                oldStudent.className.equals(newStudent.className) &&
//                oldStudent.fatherName.equals(newStudent.fatherName);
//        }
//    };
//}
//

package com.ask2784.schoolmanagemant.models

import androidx.recyclerview.widget.DiffUtil
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