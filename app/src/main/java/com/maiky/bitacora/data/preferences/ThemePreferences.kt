package com.maiky.bitacora.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_fechas_settings")

enum class AppTheme(val displayName: String) {
    BLUE("Azul"),
    PURPLE("Morado"),
    GREEN("Verde"),
    RED("Rojo")
}

enum class DarkModeOption(val displayName: String) {
    SYSTEM("Sistema"),
    LIGHT("Claro"),
    DARK("Oscuro")
}

data class ThemeSettings(
    val appTheme: AppTheme = AppTheme.BLUE,
    val darkMode: DarkModeOption = DarkModeOption.SYSTEM
)

@Singleton
class ThemePreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val themeKey = stringPreferencesKey("app_theme")
    private val darkModeKey = stringPreferencesKey("dark_mode")

    val themeSettings: Flow<ThemeSettings> = context.dataStore.data.map { prefs ->
        ThemeSettings(
            appTheme = try {
                AppTheme.valueOf(prefs[themeKey] ?: AppTheme.BLUE.name)
            } catch (_: Exception) {
                AppTheme.BLUE
            },
            darkMode = try {
                DarkModeOption.valueOf(prefs[darkModeKey] ?: DarkModeOption.SYSTEM.name)
            } catch (_: Exception) {
                DarkModeOption.SYSTEM
            }
        )
    }

    suspend fun setAppTheme(theme: AppTheme) {
        context.dataStore.edit { prefs ->
            prefs[themeKey] = theme.name
        }
    }

    suspend fun setDarkMode(mode: DarkModeOption) {
        context.dataStore.edit { prefs ->
            prefs[darkModeKey] = mode.name
        }
    }
}
