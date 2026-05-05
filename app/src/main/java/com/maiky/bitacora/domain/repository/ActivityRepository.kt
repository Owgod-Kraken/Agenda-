package com.maiky.bitacora.domain.repository

import com.maiky.bitacora.domain.model.Activity
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {
    fun getActivitiesByDate(date: String): Flow<List<Activity>>
    suspend fun getActivityById(id: Long): Activity?
    fun searchActivities(query: String): Flow<List<Activity>>
    fun getActivitiesByDateAndStatus(date: String, isCompleted: Boolean): Flow<List<Activity>>
    fun getActivityCountByDate(date: String): Flow<Int>
    fun getCompletedCountByDate(date: String): Flow<Int>
    fun getDatesWithActivities(): Flow<List<String>>
    suspend fun insertActivity(activity: Activity): Long
    suspend fun updateActivity(activity: Activity)
    suspend fun deleteActivity(activity: Activity)
    suspend fun deleteActivityById(id: Long)
    suspend fun getPendingActivitiesWithTime(date: String): List<Activity>
    suspend fun getAllPendingActivitiesWithTime(): List<Activity>
}
