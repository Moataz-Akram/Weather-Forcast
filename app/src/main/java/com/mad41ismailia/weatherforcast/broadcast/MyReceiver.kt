package com.mad41ismailia.weatherforcast.broadcast

import android.annotation.SuppressLint
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.AlarmData
import com.mad41ismailia.weatherforcast.repo.Repository
import kotlinx.coroutines.*

@SuppressLint("LogNotTimber")
class MyReceiver : BroadcastReceiver() {
private  var alarm: AlarmData? = null
private lateinit var myContext:Context
    override fun onReceive(context: Context, intent: Intent) {
        myContext = context
        val id = intent.getStringExtra("ID")
        Repository.createObject(context.applicationContext as Application)
        val repository = Repository.getRepoObject()
        Log.i("alarmalarm", "from receiver coming id $id")


        CoroutineScope(Dispatchers.Default).launch {
            runBlocking {
                alarm = repository.getAlarm(id)
            }
            delay(500)
            Log.i("alarmalarm", "from receiver alarm $alarm")
            val currentData = repository.getCurrentData()
            val condition = alarm!!.condition
            val value = alarm!!.value
            val maxDeg = currentData!!.daily[0].temp.max.toInt()
            val minDeg = currentData!!.daily[0].temp.min.toInt()
            val res = context.resources
            Log.i("alarmalarm", "from receiver condition $condition value $value min $minDeg max $maxDeg")
            if(condition.equals(res.getString(R.string.temp_more))){
                Log.i("alarmalarm", "from receiver inside first if")
                if (value>maxDeg){
                    Log.i("alarmalarm", "from receiver inside true condition")
                    startNotification(condition,value)
                }
            }else{
                Log.i("alarmalarm", "from receiver condition inside else")
                if (value<minDeg){
                    Log.i("alarmalarm", "from receiver else inside true condition")
                    Log.i("alarmalarm", "from receiver condition $condition value $value min $minDeg max $maxDeg")
                    startNotification(condition,value)
                }
            }
        }
    }

    private fun startNotification(condition:String, value:Int){
        val builder : NotificationCompat.Builder = NotificationCompat.Builder(myContext, "ALARM_CHANNEL")
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(myContext.resources.getString(R.string.weather_alarm))
            .setContentText("$condition $value")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        val notificationManagerCompat:NotificationManagerCompat = NotificationManagerCompat.from(myContext)
        notificationManagerCompat.notify(33, builder.build())
        startAudio()
    }

    private fun startAudio() {
        var mMediaPlayer = MediaPlayer()
        mMediaPlayer = MediaPlayer.create(myContext, R.raw.notification_up)
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mMediaPlayer.start()
        Thread.sleep(5000)
        mMediaPlayer.stop()
    }

}