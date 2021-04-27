package com.example.fedorinchik_hw8.workers

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.fedorinchik_hw8.ID_CHANNEL

class Battery(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val notification = NotificationCompat.Builder(applicationContext, ID_CHANNEL)
            .setContentTitle("Заряд батареи")
            .setContentText("Текущий заряд: " + currentBattery() + " %")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
        with(NotificationManagerCompat.from(applicationContext)) {
            notify(1, notification.build())
        }
        return Result.success()
    }

    private fun currentBattery(): String {
        val intent: Intent? =
            applicationContext.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val maxBatLevel = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, 100)
        val batteryLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
        val divBatPercent = batteryLevel!! * 100/ maxBatLevel!!
        return divBatPercent.toString()
    }

}