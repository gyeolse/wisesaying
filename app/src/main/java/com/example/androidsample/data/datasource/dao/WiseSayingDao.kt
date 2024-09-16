package com.example.androidsample.data.datasource.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.androidsample.data.model.Todo
import com.example.androidsample.data.model.WiseSaying
import kotlinx.coroutines.flow.Flow

@Dao
interface WiseSayingDao {
    @Query("SELECT * FROM wisesaying")
    suspend fun getAll(): List<WiseSaying>

    @Update
    suspend fun updateIsFavorite(wiseSaying: WiseSaying)

    @Query("SELECT * FROM wisesaying WHERE isFavorite == 1")
    suspend fun getFavoriteList() : List<WiseSaying>

    @Query("SELECT * FROM wisesaying WHERE contents LIKE :query OR author LIKE :query")
    fun searchWiseSayings(query: String): Flow<List<WiseSaying>>
}
