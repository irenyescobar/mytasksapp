package com.irenyescobar.mytasksapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ireny.randon.frasle.warrantyreport.data.room.converters.DateConverter
import com.irenyescobar.mytasksapp.data.room.dao.TaskDao
import com.irenyescobar.mytasksapp.data.room.entities.Task

@Database(entities = [Task::class],
          version = 1,
          exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class MyTasksAppRoomDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}