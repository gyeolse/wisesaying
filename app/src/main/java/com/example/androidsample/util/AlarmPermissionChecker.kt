package com.example.androidsample.util

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.androidsample.MainActivity

class AlarmPermissionChecker {
    companion object {
        fun checkAndRequestPostPushNotificationPermission(context: Context) {
            if (!isHasPostPushNotificationPermission(context)) {
                requestPostPushPermissionToMainActivity(context)
            } else {
            }
        }

        fun requestPostPushPermissionToMainActivity(context: Context) {
            Log.d(TAG, " Need Permissions")
            val activityIntent = Intent(context, MainActivity::class.java).apply {
                // FLAG_ACTIVITY_NEW_TASK 플래그 추가
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra("request_permission", true)
            }
            context.startActivity(activityIntent)  // Activity 시작
            return
        }

        fun isHasPostPushNotificationPermission(context: Context) : Boolean {
            return if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, " Need Permissions")
                false
            } else {
                Log.d(TAG, " Already Have Permissions")
                true
            }
        }

        @RequiresApi(Build.VERSION_CODES.S)
        fun checkAndRequestDailyNotificationPermission(context: Context) {
            if (!isDailyNoficationPermission(context)) {
                requestExactAlarmPermission(context)
            } else {

            }
        }

        fun isDailyNoficationPermission(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.canScheduleExactAlarms()  // true면 권한 있음, false면 권한 없음
            } else {
                Log.d(TAG, " Already Have Permissions")
                true  // Android 12 이하에서는 권한 필요 없음
            }
        }

        @RequiresApi(Build.VERSION_CODES.S)
        fun requestExactAlarmPermission(context: Context) {
            Toast.makeText(context, "정확한 알람을 허용하려면 설정에서 권한을 부여해주세요.", Toast.LENGTH_LONG).show()

            // 설정 화면으로 이동하는 Intent에 FLAG_ACTIVITY_NEW_TASK 추가
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                data = Uri.parse("package:" + context.packageName)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }

        @RequiresApi(Build.VERSION_CODES.S)
        suspend fun requestPermissionsIfNeeded(
            isPostPushNotificationPermission: Boolean, isHasAlarmPermission: Boolean, context: Context): Boolean {
            var allPermissionsGranted = true

            if (!isPostPushNotificationPermission) {
                // 권한 요청 함수 호출
                AlarmPermissionChecker.requestPostPushPermissionToMainActivity(context)
                // 권한이 허용될 때까지 대기
                allPermissionsGranted = allPermissionsGranted && AlarmPermissionChecker.isHasPostPushNotificationPermission(context)
            }

            if (!isHasAlarmPermission) {
                // 권한 요청 함수 호출
                AlarmPermissionChecker.requestExactAlarmPermission(context)
                // 권한이 허용될 때까지 대기
                allPermissionsGranted = allPermissionsGranted && AlarmPermissionChecker.isDailyNoficationPermission(context)
            }

            return allPermissionsGranted
        }
        const val TAG = "AlarmPermissionChecker"
    }
}