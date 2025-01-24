package com.seyefactory.wisespoon.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seyefactory.wisespoon.data.datasource.dao.TodoDao
import com.seyefactory.wisespoon.data.model.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}

