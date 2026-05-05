package com.maiky.bitacora.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maiky.bitacora.data.local.dao.ActivityDao
import com.maiky.bitacora.data.local.entity.ActivityEntity

@Database(
    entities = [ActivityEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao

    companion object {
        const val DATABASE_NAME = "bitacora_db"
    }
}
