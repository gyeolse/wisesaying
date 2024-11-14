package com.example.androidsample.receiver

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import java.util.Calendar
import android.provider.Settings

class AlarmScheduler(private val context: Context) {
    // 알림 예약 함수
    @SuppressLint("ServiceCast", "ScheduleExactAlarm")
    fun scheduleDailyNotification() {
        if (hasExactAlarmPermission(context)) {
            Log.d("AlarmScheduler", "권한 설정 완료")
            setExactAlarm()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Log.d("AlarmScheduler", "권한 설정 필요")
                checkAndRequestExactAlarmPermission()
            } else {
                Log.d("AlarmScheduler", "권한 설정 완료")
                setExactAlarm()
            }
        }
    }

    private fun setExactAlarm() {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 20)  // 오후 6시 설정
            set(Calendar.MINUTE, 15)
            set(Calendar.SECOND, 0)
        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        Log.d("AlarmScheduler", "알람이 설정되었습니다: ${calendar.time}")
    }


    // 알림 취소 함수
    fun cancelDailyNotification() {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)

        Log.d("AlarmScheduler", "알람 취소됨")
    }

    private fun checkAndRequestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                Toast.makeText(context, "정확한 알람을 허용하려면 설정에서 권한을 부여해주세요.", Toast.LENGTH_LONG).show()

                // 설정 화면으로 이동하는 Intent에 FLAG_ACTIVITY_NEW_TASK 추가
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                    data = Uri.parse("package:" + context.packageName)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // 권한 요청이 필요함
                Log.d("AlarmScheduler", "POST NOTIFICATION 권한 필요")

                return
            }
        }

    }

    fun hasExactAlarmPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.canScheduleExactAlarms()  // true면 권한 있음, false면 권한 없음
        } else {
            true  // Android 12 이하에서는 권한 필요 없음
        }
    }
    companion object {
        const val TAG = "AlarmScheduler"
    }
}