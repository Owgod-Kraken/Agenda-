package com.maiky.bitacora.domain.usecase

import com.maiky.bitacora.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

data class DayStatistics(
    val totalActivities: Int,
    val completedActivities: Int,
    val completionPercentage: Float
)

class GetStatisticsUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    operator fun invoke(date: String): Flow<DayStatistics> =
        combine(
            repository.getActivityCountByDate(date),
            repository.getCompletedCountByDate(date)
        ) { total, completed ->
            DayStatistics(
                totalActivities = total,
                completedActivities = completed,
                completionPercentage = if (total > 0) (completed.toFloat() / total) * 100f else 0f
            )
        }
}
