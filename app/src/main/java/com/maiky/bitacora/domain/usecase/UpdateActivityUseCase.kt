package com.maiky.bitacora.domain.usecase

import com.maiky.bitacora.domain.model.Activity
import com.maiky.bitacora.domain.repository.ActivityRepository
import javax.inject.Inject

class UpdateActivityUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    suspend operator fun invoke(activity: Activity) {
        require(activity.title.isNotBlank()) { "El título no puede estar vacío" }
        repository.updateActivity(activity)
    }
}
