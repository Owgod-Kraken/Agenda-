package com.maiky.bitacora.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val date: String,
    val time: String?,
    val isCompleted: Boolean = false,
    val category: String = "Personal",
    val location: String = "",
    val reminderMinutes: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)
