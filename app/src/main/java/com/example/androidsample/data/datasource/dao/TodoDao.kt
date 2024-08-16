package com.example.androidsample.data.datasource.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.androidsample.data.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    // Flow 로 비동기 데이터 처리
    @Query("SELECT * FROM todo ORDER BY date DESC")
    fun todos(): Flow<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)
}