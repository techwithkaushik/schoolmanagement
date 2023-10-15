package com.ask2784.schoolmanagement.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ask2784.schoolmanagement.dao.StudentDao
import com.ask2784.schoolmanagement.dao.StudentResultDao
import com.ask2784.schoolmanagement.models.Student
import com.ask2784.schoolmanagement.models.StudentResult

@Database(entities = [Student::class, StudentResult::class], version = 2, exportSchema = false)
abstract class DatabaseClients : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun studentResultDao(): StudentResultDao

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