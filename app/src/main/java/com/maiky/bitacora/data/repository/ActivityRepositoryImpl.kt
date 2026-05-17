package com.maiky.bitacora.data.repository

import com.maiky.bitacora.data.local.dao.ActivityDao
import com.maiky.bitacora.data.local.dao.CategoryCount
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

    override fun getActivitiesByMonth(yearMonth: String): Flow<List<Activity>> =
        dao.getActivitiesByMonth(yearMonth).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getActivitiesByDateRange(startDate: String, endDate: String): Flow<List<Activity>> =
        dao.getActivitiesByDateRange(startDate, endDate).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getActivitiesByCategory(category: String): Flow<List<Activity>> =
        dao.getActivitiesByCategory(category).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getMonthlyActivityCount(yearMonth: String): Flow<Int> =
        dao.getMonthlyActivityCount(yearMonth)

    override fun getMonthlyCompletedCount(yearMonth: String): Flow<Int> =
        dao.getMonthlyCompletedCount(yearMonth)

    override fun getCategoryCountsByMonth(yearMonth: String): Flow<List<CategoryCount>> =
        dao.getCategoryCountsByMonth(yearMonth)

    override fun getAllActivities(): Flow<List<Activity>> =
        dao.getAllActivities().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getUpcomingActivities(today: String, limit: Int): Flow<List<Activity>> =
        dao.getUpcomingActivities(today, limit).map { entities ->
            entities.map { it.toDomain() }
        }

    private fun ActivityEntity.toDomain() = Activity(
        id = id,
        title = title,
        description = description,
        date = date,
        time = time,
        isCompleted = isCompleted,
        category = category,
        location = location,
        reminderMinutes = reminderMinutes,
        createdAt = createdAt
    )

    private fun Activity.toEntity() = ActivityEntity(
        id = id,
        title = title,
        description = description,
        date = date,
        time = time,
        isCompleted = isCompleted,
        category = category,
        location = location,
        reminderMinutes = reminderMinutes,
        createdAt = createdAt
    )
}
