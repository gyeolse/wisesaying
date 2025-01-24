package com.seyefactory.wisespoon.domain.repository

import android.app.Application
import androidx.room.Room
import com.seyefactory.wisespoon.data.datasource.database.TodoDatabase
import com.seyefactory.wisespoon.data.model.Todo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepository @Inject constructor(application: Application) {
    // application 을 넘겨받아야함.
    private val db = Room.databaseBuilder(
        application,
        TodoDatabase::class.java,
        "todo-db"
    ).build()

    fun observeTodos(): Flow<List<Todo>> {
        return db.todoDao().todos()
    }

    suspend fun addTodo(todo: Todo) {
        return db.todoDao().insert(todo)
    }

    suspend fun updateTodo(todo: Todo) {
        return db.todoDao().update(todo)
    }

    suspend fun deleteTodo(todo: Todo) {
        return db.todoDao().delete(todo)
    }
}