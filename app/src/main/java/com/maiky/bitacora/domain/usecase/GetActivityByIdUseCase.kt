package com.maiky.bitacora.domain.usecase

import com.maiky.bitacora.domain.model.Activity
import com.maiky.bitacora.domain.repository.ActivityRepository
import javax.inject.Inject

class GetActivityByIdUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    suspend operator fun invoke(id: Long): Activity? =
        repository.getActivityById(id)
}
