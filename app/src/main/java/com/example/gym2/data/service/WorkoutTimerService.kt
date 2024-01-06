package com.example.gym2.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.gym2.R
import com.example.gym2.util.getTimeStringFromDouble
import java.util.Timer
import java.util.TimerTask

class WorkoutTimerService: Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    private val timer: Timer = Timer()
    private var isTimerRunning = false

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val timeElapsed = intent.getDoubleExtra(TIME_ELAPSED, 0.0)
        timer.scheduleAtFixedRate(TimeTask(timeElapsed), 0, 1000)
        isTimerRunning = intent.getBooleanExtra(TIMER_RUNNING, false)
        return START_NOT_STICKY
    }

    private inner class TimeTask(private var timeElapsed: Double): TimerTask(){
        override fun run() {
            val intent = Intent(TIMER_UPDATED)
            timeElapsed++
            startForegroundService(timeElapsed)
            intent.putExtra(TIME_ELAPSED, timeElapsed)
            intent.putExtra(TIMER_RUNNING, isTimerRunning)
            sendBroadcast(intent)
        }

    }

    override fun stopService(name: Intent?): Boolean {
        isTimerRunning = false
        return super.stopService(name)
    }

    override fun onDestroy() {
        timer.cancel()
        isTimerRunning = false
        super.onDestroy()
    }

    private fun startForegroundService(timeElapsed: Double) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(notificationManager)

        val time = getTimeStringFromDouble(timeElapsed)

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setContentTitle("ongoing workout")
            .setContentText(time)
            .setSmallIcon(R.drawable.ic_timer)

        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

    companion object {

        const val TIMER_UPDATED = "timerUpdated"
        const val TIMER_RUNNING = "isTimerRunning"
        const val TIME_ELAPSED = "timeElapsed"
        const val NOTIFICATION_CHANNEL_ID = "notification_channel"
        const val NOTIFICATION_ID = 1
        const val NOTIFICATION_CHANNEL_NAME = "notification"
    }
}