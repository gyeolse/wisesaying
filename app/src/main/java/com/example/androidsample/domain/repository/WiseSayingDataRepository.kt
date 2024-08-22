package com.example.androidsample.domain.repository

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.androidsample.data.datasource.database.TodoDatabase
import com.example.androidsample.data.datasource.database.WiseSayingDatabase
import com.example.androidsample.data.model.Todo
import com.example.androidsample.data.model.WiseSaying
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WiseSayingDataRepository @Inject constructor(application: Application) {
    private val db: WiseSayingDatabase = WiseSayingDatabase.getDatabase(application)
    private val wiseDao = db.wiseSayingDao()

    suspend fun observeWiseSaying(): Flow<List<WiseSaying>> {
        Log.d("WiseSayingDataRepository", wiseDao.wiseSayingList().count().toString())
        return wiseDao.wiseSayingList()
    }

    suspend fun getAll() : List<WiseSaying> {
        Log.d("wiseSayingData", wiseDao.getAll().size.toString())
        return withContext(Dispatchers.IO) {
            wiseDao.getAll()
        }
//        return wiseDao.getAll()
    }
}