package com.example.androidsample.receiver

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.example.androidsample.R
import androidx.core.app.NotificationManagerCompat
import com.example.androidsample.MainActivity

// 알림을 받기 위한 BroadcastReceiver 생성
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("AlarmReceiver", "onReceive")
        showNotification(context)
        scheduleNextDayAlarm(context)
    }

    private fun scheduleNextDayAlarm(context: Context) {
        val alarmScheduler = AlarmScheduler(context)
        alarmScheduler.scheduleDailyNotification()
    }

    private fun showNotification(context: Context) {
        Log.d("AlarmReceiver", "showNotification")

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "daily_notification_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Daily Notifications", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        // 알림을 클릭했을 때 이동할 액티비티를 설정
        val intent = Intent(context, MainActivity::class.java).apply {
            // FLAG_ACTIVITY_NEW_TASK 플래그 추가
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // PendingIntent 생성
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE // API 23 이상에서는 FLAG_IMMUTABLE이 필요
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("WiseSaying")
            .setContentText("하루에 하나씩 명언을 읽어보세요!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(1, notification)
        Log.d("Notification", "Notification sent with ID: 1")
        Toast.makeText(context, "Notification Sent", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val NOTIFICATION_REQUEST_CODE = 1001 // 고유한 정수 값
    }
}