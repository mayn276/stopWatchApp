package com.example.stopwatch

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.Timer
import java.util.TimerTask
import kotlin.math.roundToInt

class StopWatchService:Service() {
    override fun onBind(intent: Intent?): IBinder?=null
    private var timer = Timer()


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val time = intent.getDoubleExtra(CURRENT_TIME,0.0)
        timer.scheduleAtFixedRate(StopWatchTimerClass(time),0,1000)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }

    companion object{
        const val CURRENT_TIME = "current time"
        const val UPDATE_TIME = "update time"
    }
   private inner class StopWatchTimerClass(private var time:Double): TimerTask(){
       override fun run() {
           val intent = Intent(UPDATE_TIME)
           time++
           intent.putExtra(CURRENT_TIME,time)
           sendBroadcast(intent)

       }

   }

    private fun getFormattedTime(time:Double):String{
        val timeInt = time.roundToInt()
        val hours = timeInt % 86400 / 3600
        val minutes = timeInt % 86400 % 3600 / 60
        val seconds = timeInt % 86400 % 3600 % 60

        return String.format("%02d:%02d:%02d",hours,minutes,seconds)
    }
}
