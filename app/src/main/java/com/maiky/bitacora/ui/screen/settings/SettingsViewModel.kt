package com.maiky.bitacora.ui.screen.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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
    application: Application
) : AndroidViewModel(application) {

    private val themePreferences = ThemePreferences.getInstance(application)

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
