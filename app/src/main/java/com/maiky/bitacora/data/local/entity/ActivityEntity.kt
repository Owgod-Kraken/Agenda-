package com.maiky.bitacora.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val date: String,       // Format: yyyy-MM-dd
    val time: String?,      // Format: HH:mm (nullable)
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
