package com.maiky.bitacora.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.maiky.bitacora.data.local.entity.ActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {

    @Query("""
        SELECT * FROM activities 
        WHERE date = :date 
        ORDER BY 
            CASE WHEN time IS NOT NULL THEN 0 ELSE 1 END,
            time ASC,
            createdAt ASC
    """)
    fun getActivitiesByDate(date: String): Flow<List<ActivityEntity>>

    @Query("SELECT * FROM activities WHERE id = :id")
    suspend fun getActivityById(id: Long): ActivityEntity?

    @Query("""
        SELECT * FROM activities 
        WHERE title LIKE '%' || :query || '%' 
           OR description LIKE '%' || :query || '%'
        ORDER BY date DESC, time ASC
    """)
    fun searchActivities(query: String): Flow<List<ActivityEntity>>

    @Query("""
        SELECT * FROM activities 
        WHERE date = :date AND isCompleted = :isCompleted
        ORDER BY 
            CASE WHEN time IS NOT NULL THEN 0 ELSE 1 END,
            time ASC,
            createdAt ASC
    """)
    fun getActivitiesByDateAndStatus(date: String, isCompleted: Boolean): Flow<List<ActivityEntity>>

    @Query("SELECT COUNT(*) FROM activities WHERE date = :date")
    fun getActivityCountByDate(date: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM activities WHERE date = :date AND isCompleted = 1")
    fun getCompletedCountByDate(date: String): Flow<Int>

    @Query("SELECT DISTINCT date FROM activities ORDER BY date DESC")
    fun getDatesWithActivities(): Flow<List<String>>

    @Query("SELECT COUNT(*) FROM activities WHERE date = :date")
    suspend fun getActivityCountForDate(date: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: ActivityEntity): Long

    @Update
    suspend fun updateActivity(activity: ActivityEntity)

    @Delete
    suspend fun deleteActivity(activity: ActivityEntity)

    @Query("DELETE FROM activities WHERE id = :id")
    suspend fun deleteActivityById(id: Long)

    @Query("SELECT * FROM activities WHERE date = :date AND time IS NOT NULL AND isCompleted = 0")
    suspend fun getPendingActivitiesWithTime(date: String): List<ActivityEntity>

    @Query("SELECT * FROM activities WHERE time IS NOT NULL AND isCompleted = 0")
    suspend fun getAllPendingActivitiesWithTime(): List<ActivityEntity>
}
