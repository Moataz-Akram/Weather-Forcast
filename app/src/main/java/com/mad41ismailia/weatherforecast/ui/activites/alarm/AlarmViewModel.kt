package com.mad41ismailia.weatherforecast.ui.activites.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModel
import com.mad41ismailia.weatherforecast.entity.DatabaseClasses.AlarmData
import com.mad41ismailia.weatherforecast.repo.Repository

class AlarmViewModel : ViewModel() {
    val repo = Repository.getRepoObject()

    fun getUnits():String{
        return repo.getUnits()
    }

    fun addAlarmToDB(newAlarm: AlarmData) {
        repo.addAlarmToDB(newAlarm)
    }



    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val description = "Channel"
            val notificationChannel = NotificationChannel(
                "ALARM_CHANNEL",
                "Alarm_Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.description = description
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}