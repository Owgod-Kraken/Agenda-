package com.maiky.bitacora.domain.usecase

import com.maiky.bitacora.domain.repository.ActivityRepository
import javax.inject.Inject

class ToggleCompleteUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    suspend operator fun invoke(id: Long) {
        val activity = repository.getActivityById(id) ?: return
        repository.updateActivity(activity.copy(isCompleted = !activity.isCompleted))
    }
}
