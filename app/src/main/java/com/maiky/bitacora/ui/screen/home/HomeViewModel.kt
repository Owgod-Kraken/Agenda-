package com.maiky.bitacora.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maiky.bitacora.domain.model.Activity
import com.maiky.bitacora.domain.usecase.DeleteActivityUseCase
import com.maiky.bitacora.domain.usecase.DayStatistics
import com.maiky.bitacora.domain.usecase.GetActivitiesByDateUseCase
import com.maiky.bitacora.domain.usecase.GetStatisticsUseCase
import com.maiky.bitacora.domain.usecase.ToggleCompleteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

enum class FilterType {
    ALL, PENDING, COMPLETED
}

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getActivitiesByDate: GetActivitiesByDateUseCase,
    private val deleteActivity: DeleteActivityUseCase,
    private val toggleComplete: ToggleCompleteUseCase,
    private val getStatistics: GetStatisticsUseCase
) : ViewModel() {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _filterType = MutableStateFlow(FilterType.ALL)
    val filterType: StateFlow<FilterType> = _filterType.asStateFlow()

    val formattedDate: StateFlow<String> = _selectedDate
        .combine(MutableStateFlow(Unit)) { date, _ ->
            date.format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM yyyy"))
                .replaceFirstChar { it.uppercase() }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val activities: StateFlow<List<Activity>> =
        combine(_selectedDate, _filterType) { date, filter ->
            Pair(date, filter)
        }.flatMapLatest { (date, filter) ->
            val dateStr = date.format(dateFormatter)
            when (filter) {
                FilterType.ALL -> getActivitiesByDate(dateStr)
                FilterType.PENDING -> getActivitiesByDate.byStatus(dateStr, false)
                FilterType.COMPLETED -> getActivitiesByDate.byStatus(dateStr, true)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val statistics: StateFlow<DayStatistics> =
        _selectedDate.flatMapLatest { date ->
            getStatistics(date.format(dateFormatter))
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            DayStatistics(0, 0, 0f)
        )

    fun selectDate(year: Int, month: Int, dayOfMonth: Int) {
        _selectedDate.value = LocalDate.of(year, month, dayOfMonth)
    }

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun selectDateFromMillis(millis: Long) {
        val date = java.time.Instant.ofEpochMilli(millis)
            .atZone(java.time.ZoneId.of("UTC"))
            .toLocalDate()
        _selectedDate.value = date
    }

    fun goToPreviousDay() {
        _selectedDate.value = _selectedDate.value.minusDays(1)
    }

    fun goToNextDay() {
        _selectedDate.value = _selectedDate.value.plusDays(1)
    }

    fun goToToday() {
        _selectedDate.value = LocalDate.now()
    }

    fun setFilter(filter: FilterType) {
        _filterType.value = filter
    }

    fun onToggleComplete(activityId: Long) {
        viewModelScope.launch {
            toggleComplete(activityId)
        }
    }

    fun onDelete(activityId: Long) {
        viewModelScope.launch {
            deleteActivity(activityId)
        }
    }

    fun getDateString(): String = _selectedDate.value.format(dateFormatter)
}
