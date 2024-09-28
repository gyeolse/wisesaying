package com.example.androidsample.domain.repository

import android.app.Application
import android.util.Log
import com.example.androidsample.data.datasource.database.WiseSayingDatabase
import WiseSayingDataStore
import com.example.androidsample.data.model.WiseSaying
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WiseSayingDataRepository @Inject constructor(application: Application) {
    private val db: WiseSayingDatabase = WiseSayingDatabase.getDatabase(application)
    private val wiseDao = db.wiseSayingDao()
    private val wiseSayingDataStore = WiseSayingDataStore(application)
    suspend fun getAll() : List<WiseSaying> {
        Log.d("wiseSayingData", wiseDao.getAll().size.toString())
        return withContext(Dispatchers.IO) {
            wiseDao.getAll()
        }
    }

    suspend fun getFavoriteList(): List<WiseSaying> {
        Log.d(TAG, wiseDao.getFavoriteList().size.toString())
        return withContext(Dispatchers.IO) {
            wiseDao.getFavoriteList()
        }
    }

    suspend fun updateIsFavorite(wiseSaying: WiseSaying) {
        Log.d(TAG, " Current updateIsFavorite value is=" + wiseSaying.isFavorite)
        return wiseDao.updateIsFavorite(wiseSaying)
    }

    fun searchWiseSayings(query: String): Flow<List<WiseSaying>> {
        return wiseDao.searchWiseSayings("%$query%")
    }

    // 추가: 테마 설정 저장
    suspend fun saveThemePreference(isDarkMode: Boolean) {
        wiseSayingDataStore.saveThemePreference(isDarkMode)
    }

    // 추가: 테마 설정 불러오기
    fun getThemePreference(): Flow<Boolean> {
        return wiseSayingDataStore.getThemePreference()
    }

    // 추가: Push 알림 설정 저장
    suspend fun savePushNotificationPreference(isEnabled: Boolean) {
        wiseSayingDataStore.savePushNotificationPreference(isEnabled)
    }

    // 추가: Push 알림 설정 불러오기
    fun getPushNotificationPreference(): Flow<Boolean> {
        return wiseSayingDataStore.getPushNotificationPreference()
    }

    companion object {
        const val TAG = "WiseSyaingDataRepository"
    }
}