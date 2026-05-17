package com.maiky.bitacora.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.maiky.bitacora.data.local.dao.ActivityDao
import com.maiky.bitacora.data.local.entity.ActivityEntity

@Database(
    entities = [ActivityEntity::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao

    companion object {
        const val DATABASE_NAME = "bitacora_db"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE activities ADD COLUMN category TEXT NOT NULL DEFAULT 'Personal'")
                db.execSQL("ALTER TABLE activities ADD COLUMN location TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE activities ADD COLUMN reminderMinutes INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}
