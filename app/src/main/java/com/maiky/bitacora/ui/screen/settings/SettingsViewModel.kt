package com.maiky.bitacora.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maiky.bitacora.data.preferences.AppTheme
import com.maiky.bitacora.data.preferences.DarkModeOption
import com.maiky.bitacora.data.preferences.ThemePreferences
import com.maiky.bitacora.data.preferences.ThemeSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themePreferences: ThemePreferences
) : ViewModel() {

    val themeSettings: StateFlow<ThemeSettings> = themePreferences.themeSettings
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ThemeSettings()
        )

    fun setTheme(theme: AppTheme) {
        viewModelScope.launch {
            themePreferences.setAppTheme(theme)
        }
    }

    fun setDarkMode(darkMode: DarkModeOption) {
        viewModelScope.launch {
            themePreferences.setDarkMode(darkMode)
        }
    }
}
