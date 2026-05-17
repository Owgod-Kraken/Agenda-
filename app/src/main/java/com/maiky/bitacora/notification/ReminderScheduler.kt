package com.maiky.bitacora.notification

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.maiky.bitacora.domain.model.Activity
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun scheduleReminder(activity: Activity) {
        val time = activity.time ?: return
        val date = activity.date

        try {
            val dateTime = LocalDateTime.of(
                LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))
            )

            val reminderMinutes = if (activity.reminderMinutes > 0) activity.reminderMinutes.toLong() else 0L
            val reminderTime = dateTime.minusMinutes(reminderMinutes)

            val now = LocalDateTime.now()
            val delay = Duration.between(now, reminderTime).toMillis()

            if (delay <= 0) return

            val data = Data.Builder()
                .putLong(ReminderWorker.KEY_ACTIVITY_ID, activity.id)
                .putString(ReminderWorker.KEY_TITLE, activity.title)
                .putString(ReminderWorker.KEY_DESCRIPTION, activity.description)
                .putString(ReminderWorker.KEY_TIME, time)
                .build()

            val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setInputData(data)
                .addTag("reminder_${activity.id}")
                .build()

            WorkManager.getInstance(context)
                .enqueueUniqueWork(
                    "reminder_${activity.id}",
                    ExistingWorkPolicy.REPLACE,
                    workRequest
                )
        } catch (_: Exception) {
            // Invalid date/time format
        }
    }

    fun cancelReminder(activityId: Long) {
        WorkManager.getInstance(context)
            .cancelUniqueWork("reminder_$activityId")
    }
}
