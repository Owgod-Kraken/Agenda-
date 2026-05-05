package com.maiky.bitacora.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.maiky.bitacora.data.local.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action != Intent.ACTION_BOOT_COMPLETED) return

        val scheduler = ReminderScheduler(context)
        val db = androidx.room.Room.databaseBuilder(
            context, AppDatabase::class.java, AppDatabase.DATABASE_NAME
        ).build()

        CoroutineScope(Dispatchers.IO).launch {
            val activities = db.activityDao().getAllPendingActivitiesWithTime()
            activities.forEach { entity ->
                scheduler.scheduleReminder(
                    com.maiky.bitacora.domain.model.Activity(
                        id = entity.id,
                        title = entity.title,
                        description = entity.description,
                        date = entity.date,
                        time = entity.time,
                        isCompleted = entity.isCompleted,
                        createdAt = entity.createdAt
                    )
                )
            }
            db.close()
        }
    }
}
