package com.seyefactory.wisespoon.receiver

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.Calendar
import com.seyefactory.wisespoon.receiver.AlarmReceiver.Companion.NOTIFICATION_REQUEST_CODE

class AlarmScheduler(private val context: Context) {
    fun isNotificationScheduled(): Boolean {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            NOTIFICATION_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE // 기존 PendingIntent가 있는지 확인
        )
        return pendingIntent != null
    }

    @SuppressLint("ServiceCast", "ScheduleExactAlarm")
    fun scheduleDailyNotification() {
        setExactAlarm()
    }

    @SuppressLint("ScheduleExactAlarm")
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
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)

            // 시간이 이미 지났다면 다음 날로 설정
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
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

    companion object {
        const val TAG = "AlarmScheduler"
    }
}