package com.example.androidsample.domain.repository

import android.app.Application
import android.util.Log
import com.example.androidsample.data.datasource.database.WiseSayingDatabase
import WiseSayingDataStore
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.androidsample.data.model.WiseSaying
import com.example.androidsample.receiver.AlarmScheduler
import com.example.androidsample.util.AlarmPermissionChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WiseSayingDataRepository @Inject constructor(
    application: Application) {
    private val db: WiseSayingDatabase = WiseSayingDatabase.getDatabase(application)
    private val wiseDao = db.wiseSayingDao()
    private val wiseSayingDataStore = WiseSayingDataStore(application)
    private val alarmScheduler = AlarmScheduler(application)

    private val context: Context = application.applicationContext

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
        Log.d(TAG, " Current updateIsFavorite value is=${wiseSaying.uid}," +
                " contents= ${wiseSaying.contents}, isFavorite=${wiseSaying.isFavorite}")
        return wiseDao.updateIsFavorite(wiseSaying)
    }

    fun searchWiseSayings(query: String): Flow<List<WiseSaying>> {
        return wiseDao.searchWiseSayings("%$query%")
    }

    suspend fun saveThemePreference(isDarkMode: Boolean) {
        wiseSayingDataStore.saveThemePreference(isDarkMode)
    }

    fun getThemePreference(): Flow<Boolean> {
        return wiseSayingDataStore.getThemePreference()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    suspend fun savePushNotificationPreference(isEnabled: Boolean) {
        wiseSayingDataStore.savePushNotificationPreference(isEnabled)
        if (isEnabled) {
            val isPostPushNotificationPermission = AlarmPermissionChecker.isHasPostPushNotificationPermission(context)
            val isHasAlarmPermission = AlarmPermissionChecker.isDailyNoficationPermission(context)
            if ((isPostPushNotificationPermission) && (isHasAlarmPermission)) {
                alarmScheduler.scheduleDailyNotification()
            } else {
                Log.d("WiseSayingDataRepository", "Needs Permissions")

                // [TODO] 수정 필요. 설정 직후, 바로 알람 설정이 가능해야함.

                requestPermissionsIfNeededAndNotify(isPostPushNotificationPermission, isHasAlarmPermission) {
                    alarmScheduler.scheduleDailyNotification()
                }
            }
        } else {
            alarmScheduler.cancelDailyNotification()
        }
    }

    // 추가: Push 알림 설정 불러오기
    fun getPushNotificationPreference(): Flow<Boolean> {
        return wiseSayingDataStore.getPushNotificationPreference()
    }

    // 권한을 요청하고 결과를 콜백으로 받는 함수
    @RequiresApi(Build.VERSION_CODES.S)
    fun requestPermissionsIfNeededAndNotify(
        isPostPushNotificationPermission: Boolean,
        isHasAlarmPermission: Boolean,
        onPermissionsGranted: () -> Unit
    ) {
        if (!isPostPushNotificationPermission) {
            // 권한 요청 함수 호출
            AlarmPermissionChecker.requestPostPushPermissionToMainActivity(context)
        }

        if (!isHasAlarmPermission) {
            // 권한 요청 함수 호출
            AlarmPermissionChecker.requestExactAlarmPermission(context)
        }

        // 권한이 모두 허용되었는지 확인한 후, 알림 스케줄을 설정
        // 이 부분은 Activity에서 권한 결과를 받아 확인하는 방식으로 처리되어야 함
        val allPermissionsGranted = AlarmPermissionChecker.isHasPostPushNotificationPermission(context) &&
                AlarmPermissionChecker.isDailyNoficationPermission(context)

        if (allPermissionsGranted) {
            onPermissionsGranted()
        }
    }

    fun isNotificationScheduled(): Boolean {
        return alarmScheduler.isNotificationScheduled()
    }

    fun scheduleDailyNotification() {
        alarmScheduler.scheduleDailyNotification()
    }

    companion object {
        const val TAG = "WiseSyaingDataRepository"
    }
}