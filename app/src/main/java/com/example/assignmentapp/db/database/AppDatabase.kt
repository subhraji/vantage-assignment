package com.example.assignmentapp.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.assignmentapp.db.dao.TaskDao
import com.example.assignmentapp.db.entity.TaskModelEntity

@Database(
    entities = [TaskModelEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    companion object {
        private var instance_: AppDatabase? = null
        val instance get() = instance_!!

        /**
         * creates singleton database instance
         */
        @Synchronized
        fun initialize(context: Context): AppDatabase {
            if (instance_ == null) {
                instance_ = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }

    abstract fun getTaskDao() : TaskDao

}