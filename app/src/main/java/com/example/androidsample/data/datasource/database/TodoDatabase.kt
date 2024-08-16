package com.example.androidsample.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidsample.data.datasource.dao.TodoDao
import com.example.androidsample.data.model.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}

