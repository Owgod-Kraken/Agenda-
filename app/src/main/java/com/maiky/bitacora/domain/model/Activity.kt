package com.maiky.bitacora.domain.model

data class Activity(
    val id: Long = 0,
    val title: String,
    val description: String,
    val date: String,
    val time: String?,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
