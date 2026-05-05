package com.maiky.bitacora.domain.usecase

import com.maiky.bitacora.domain.model.Activity
import com.maiky.bitacora.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActivitiesByDateUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    operator fun invoke(date: String): Flow<List<Activity>> =
        repository.getActivitiesByDate(date)

    fun byStatus(date: String, isCompleted: Boolean): Flow<List<Activity>> =
        repository.getActivitiesByDateAndStatus(date, isCompleted)
}
