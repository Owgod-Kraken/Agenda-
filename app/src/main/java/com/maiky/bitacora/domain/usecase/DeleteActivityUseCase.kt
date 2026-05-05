package com.maiky.bitacora.domain.usecase

import com.maiky.bitacora.domain.repository.ActivityRepository
import javax.inject.Inject

class DeleteActivityUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.deleteActivityById(id)
    }
}
