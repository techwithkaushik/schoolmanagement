//package com.ask2784.schoolmanagemant.database;
//import android.content.Context;
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//import com.ask2784.schoolmanagemant.dao.StudentDao;
//import com.ask2784.schoolmanagemant.models.Student;
//
//@Database(entities = {Student.class} , version = 1 , exportSchema = false)
//public abstract class DatabaseClients extends RoomDatabase{
//    public abstract StudentDao studentDao();
//    private static volatile DatabaseClients INSTANCE;
//    
//    static DatabaseClients getDatabase(Context context){
//        if(INSTANCE == null) {
//        	synchronized (DatabaseClients.class) {
//                if(INSTANCE == null) {
//                	INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseClients.class,"school_management")
//                                .allowMainThreadQueries()
//                                .fallbackToDestructiveMigration()
//                                .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
//}
//

package com.ask2784.schoolmanagemant.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ask2784.schoolmanagemant.dao.StudentDao
import com.ask2784.schoolmanagemant.models.Student

@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class DatabaseClients : RoomDatabase() {
    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile private var INSTANCE: DatabaseClients? = null
        
        fun getDatabase(context: Context): DatabaseClients = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, DatabaseClients::class.java, "school_management"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
}