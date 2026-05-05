package com.maiky.bitacora.data.repository

import com.maiky.bitacora.data.local.dao.ActivityDao
import com.maiky.bitacora.data.local.entity.ActivityEntity
import com.maiky.bitacora.domain.model.Activity
import com.maiky.bitacora.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityRepositoryImpl @Inject constructor(
    private val dao: ActivityDao
) : ActivityRepository {

    override fun getActivitiesByDate(date: String): Flow<List<Activity>> =
        dao.getActivitiesByDate(date).map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun getActivityById(id: Long): Activity? =
        dao.getActivityById(id)?.toDomain()

    override fun searchActivities(query: String): Flow<List<Activity>> =
        dao.searchActivities(query).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getActivitiesByDateAndStatus(date: String, isCompleted: Boolean): Flow<List<Activity>> =
        dao.getActivitiesByDateAndStatus(date, isCompleted).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getActivityCountByDate(date: String): Flow<Int> =
        dao.getActivityCountByDate(date)

    override fun getCompletedCountByDate(date: String): Flow<Int> =
        dao.getCompletedCountByDate(date)

    override fun getDatesWithActivities(): Flow<List<String>> =
        dao.getDatesWithActivities()

    override suspend fun insertActivity(activity: Activity): Long =
        dao.insertActivity(activity.toEntity())

    override suspend fun updateActivity(activity: Activity) =
        dao.updateActivity(activity.toEntity())

    override suspend fun deleteActivity(activity: Activity) =
        dao.deleteActivity(activity.toEntity())

    override suspend fun deleteActivityById(id: Long) =
        dao.deleteActivityById(id)

    override suspend fun getPendingActivitiesWithTime(date: String): List<Activity> =
        dao.getPendingActivitiesWithTime(date).map { it.toDomain() }

    override suspend fun getAllPendingActivitiesWithTime(): List<Activity> =
        dao.getAllPendingActivitiesWithTime().map { it.toDomain() }

    private fun ActivityEntity.toDomain() = Activity(
        id = id,
        title = title,
        description = description,
        date = date,
        time = time,
        isCompleted = isCompleted,
        createdAt = createdAt
    )

    private fun Activity.toEntity() = ActivityEntity(
        id = id,
        title = title,
        description = description,
        date = date,
        time = time,
        isCompleted = isCompleted,
        createdAt = createdAt
    )
}
