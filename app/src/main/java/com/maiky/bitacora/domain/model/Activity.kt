package com.maiky.bitacora.domain.model

data class Activity(
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

enum class EventCategory(val displayName: String, val colorHex: Long) {
    TRABAJO("Trabajo", 0xFF1976D2),
    ESCUELA("Escuela", 0xFF7B1FA2),
    PERSONAL("Personal", 0xFF388E3C),
    SALUD("Salud", 0xFFD32F2F),
    REUNIONES("Reuniones", 0xFFF57C00);

    companion object {
        fun fromDisplayName(name: String): EventCategory =
            entries.find { it.displayName == name } ?: PERSONAL
    }
}
