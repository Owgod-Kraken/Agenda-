package com.maiky.bitacora.ui.screen.addedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maiky.bitacora.domain.model.Activity
import com.maiky.bitacora.domain.usecase.AddActivityUseCase
import com.maiky.bitacora.domain.usecase.GetActivityByIdUseCase
import com.maiky.bitacora.domain.usecase.UpdateActivityUseCase
import com.maiky.bitacora.notification.ReminderScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class AddEditState(
    val title: String = "",
    val description: String = "",
    val date: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    val time: String? = null,
    val enableReminder: Boolean = false,
    val isEditing: Boolean = false,
    val titleError: String? = null,
    val isLoading: Boolean = false
)

sealed class AddEditEvent {
    data object SaveSuccess : AddEditEvent()
    data class Error(val message: String) : AddEditEvent()
}

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val addActivity: AddActivityUseCase,
    private val updateActivity: UpdateActivityUseCase,
    private val getActivityById: GetActivityByIdUseCase,
    private val reminderScheduler: ReminderScheduler,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val activityId: Long = savedStateHandle.get<Long>("activityId") ?: -1L
    private val dateArg: String = savedStateHandle.get<String>("date") ?: ""

    private val _state = MutableStateFlow(AddEditState())
    val state: StateFlow<AddEditState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<AddEditEvent>()
    val events: SharedFlow<AddEditEvent> = _events.asSharedFlow()

    private var currentActivity: Activity? = null

    init {
        if (activityId != -1L) {
            loadActivity()
        } else if (dateArg.isNotBlank()) {
            _state.value = _state.value.copy(date = dateArg)
        }
    }

    private fun loadActivity() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val activity = getActivityById(activityId)
            if (activity != null) {
                currentActivity = activity
                _state.value = _state.value.copy(
                    title = activity.title,
                    description = activity.description,
                    date = activity.date,
                    time = activity.time,
                    isEditing = true,
                    isLoading = false
                )
            } else {
                _state.value = _state.value.copy(isLoading = false)
                _events.emit(AddEditEvent.Error("Actividad no encontrada"))
            }
        }
    }

    fun onTitleChange(title: String) {
        _state.value = _state.value.copy(title = title, titleError = null)
    }

    fun onDescriptionChange(description: String) {
        _state.value = _state.value.copy(description = description)
    }

    fun onDateChange(date: String) {
        _state.value = _state.value.copy(date = date)
    }

    fun onTimeChange(time: String?) {
        _state.value = _state.value.copy(time = time)
    }

    fun onReminderToggle(enabled: Boolean) {
        _state.value = _state.value.copy(enableReminder = enabled)
    }

    fun onSave() {
        val currentState = _state.value

        if (currentState.title.isBlank()) {
            _state.value = currentState.copy(titleError = "El título es obligatorio")
            return
        }

        viewModelScope.launch {
            try {
                if (currentState.isEditing && currentActivity != null) {
                    val updated = currentActivity!!.copy(
                        title = currentState.title.trim(),
                        description = currentState.description.trim(),
                        date = currentState.date,
                        time = currentState.time
                    )
                    updateActivity(updated)

                    if (currentState.enableReminder && currentState.time != null) {
                        reminderScheduler.scheduleReminder(updated)
                    }
                } else {
                    val activity = Activity(
                        title = currentState.title.trim(),
                        description = currentState.description.trim(),
                        date = currentState.date,
                        time = currentState.time
                    )
                    val id = addActivity(activity)

                    if (currentState.enableReminder && currentState.time != null) {
                        reminderScheduler.scheduleReminder(activity.copy(id = id))
                    }
                }
                _events.emit(AddEditEvent.SaveSuccess)
            } catch (e: Exception) {
                _events.emit(AddEditEvent.Error(e.message ?: "Error al guardar"))
            }
        }
    }
}
