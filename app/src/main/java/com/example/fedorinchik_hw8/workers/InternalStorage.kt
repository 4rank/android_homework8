package com.example.fedorinchik_hw8.workers

import android.content.Context
import android.os.Environment
import android.os.StatFs
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.fedorinchik_hw8.ID_CHANNEL

class InternalStorage(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val notification = NotificationCompat.Builder(applicationContext, ID_CHANNEL)
            .setContentTitle("Внутренее хранилище")
            .setContentText("Оставшаяся память: " + InternalStorageSize() + " MB")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
        with(NotificationManagerCompat.from(applicationContext)) {
            notify(0, notification.build())
        }
        return Result.success()
    }

    private fun InternalStorageSize(): String {
        val statFs = StatFs(Environment.getDataDirectory().path)
        val bites: Long =
            statFs.blockSizeLong * statFs.availableBlocksLong
        val byte = bites / 1024
        val megabyte = byte / 1024
        return megabyte.toString()
    }

}