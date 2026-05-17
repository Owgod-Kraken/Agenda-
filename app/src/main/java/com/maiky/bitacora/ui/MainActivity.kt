package com.maiky.bitacora.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.maiky.bitacora.data.preferences.ThemePreferences
import com.maiky.bitacora.data.preferences.ThemeSettings
import com.maiky.bitacora.ui.navigation.BitacoraNavGraph
import com.maiky.bitacora.ui.theme.BitacoraDeMaikyTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var themePreferences: ThemePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val themeSettings by themePreferences.themeSettings.collectAsState(
                initial = ThemeSettings()
            )

            BitacoraDeMaikyTheme(
                appTheme = themeSettings.appTheme,
                darkModeOption = themeSettings.darkMode
            ) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    BitacoraNavGraph(navController = navController)
                }
            }
        }
    }
}
