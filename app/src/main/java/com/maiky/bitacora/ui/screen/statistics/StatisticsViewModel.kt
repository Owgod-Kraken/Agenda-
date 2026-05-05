package com.maiky.bitacora.ui.screen.statistics

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maiky.bitacora.domain.model.Activity
import com.maiky.bitacora.domain.usecase.DayStatistics
import com.maiky.bitacora.domain.usecase.GetActivitiesByDateUseCase
import com.maiky.bitacora.domain.usecase.GetStatisticsUseCase
import com.maiky.bitacora.domain.repository.ActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getStatistics: GetStatisticsUseCase,
    private val getActivitiesByDate: GetActivitiesByDateUseCase,
    private val repository: ActivityRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val dateArg: String = savedStateHandle.get<String>("date")
        ?: LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    private val _selectedDate = MutableStateFlow(dateArg)
    val selectedDate: StateFlow<String> = _selectedDate.asStateFlow()

    val statistics: StateFlow<DayStatistics> = _selectedDate
        .flatMapLatest { date -> getStatistics(date) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            DayStatistics(0, 0, 0f)
        )

    val activities: StateFlow<List<Activity>> = _selectedDate
        .flatMapLatest { date -> getActivitiesByDate(date) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val datesWithActivities: StateFlow<List<String>> = repository.getDatesWithActivities()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onDateSelected(date: String) {
        _selectedDate.value = date
    }
}
