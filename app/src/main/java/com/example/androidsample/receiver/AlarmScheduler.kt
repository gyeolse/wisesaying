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

    companion object {
        const val TAG = "AlarmScheduler"
    }
}