package com.maiky.bitacora.domain.usecase

import com.maiky.bitacora.domain.model.Activity
import com.maiky.bitacora.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchActivitiesUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    operator fun invoke(query: String): Flow<List<Activity>> =
        repository.searchActivities(query)
}
