package com.maiky.bitacora.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.maiky.bitacora.data.preferences.AppTheme
import com.maiky.bitacora.data.preferences.DarkModeOption

private fun blueLight() = lightColorScheme(
    primary = BluePrimary, onPrimary = BlueOnPrimary,
    primaryContainer = BluePrimaryContainer, onPrimaryContainer = BlueOnPrimaryContainer,
    secondary = BlueSecondary, onSecondary = BlueOnSecondary,
    secondaryContainer = BlueSecondaryContainer, onSecondaryContainer = BlueOnSecondaryContainer,
    tertiary = BlueTertiary, onTertiary = BlueOnTertiary,
    tertiaryContainer = BlueTertiaryContainer, onTertiaryContainer = BlueOnTertiaryContainer,
    error = CommonError, onError = CommonOnError,
    errorContainer = CommonErrorContainer, onErrorContainer = CommonOnErrorContainer,
    background = LightBackground, onBackground = LightOnBackground,
    surface = LightSurface, onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant, onSurfaceVariant = LightOnSurfaceVariant,
    outline = LightOutline
)

private fun blueDark() = darkColorScheme(
    primary = BlueDarkPrimary, onPrimary = BlueDarkOnPrimary,
    primaryContainer = BlueDarkPrimaryContainer, onPrimaryContainer = BlueDarkOnPrimaryContainer,
    secondary = BlueDarkSecondary, onSecondary = BlueDarkOnSecondary,
    secondaryContainer = BlueDarkSecondaryContainer, onSecondaryContainer = BlueDarkOnSecondaryContainer,
    error = CommonDarkError, onError = CommonDarkOnError,
    errorContainer = CommonDarkErrorContainer, onErrorContainer = CommonDarkOnErrorContainer,
    background = DarkBackground, onBackground = DarkOnBackground,
    surface = DarkSurface, onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant, onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOutline
)

private fun purpleLight() = lightColorScheme(
    primary = PurplePrimary, onPrimary = PurpleOnPrimary,
    primaryContainer = PurplePrimaryContainer, onPrimaryContainer = PurpleOnPrimaryContainer,
    secondary = PurpleSecondary, onSecondary = PurpleOnSecondary,
    secondaryContainer = PurpleSecondaryContainer, onSecondaryContainer = PurpleOnSecondaryContainer,
    tertiary = PurpleTertiary, onTertiary = PurpleOnTertiary,
    tertiaryContainer = PurpleTertiaryContainer, onTertiaryContainer = PurpleOnTertiaryContainer,
    error = CommonError, onError = CommonOnError,
    errorContainer = CommonErrorContainer, onErrorContainer = CommonOnErrorContainer,
    background = LightBackground, onBackground = LightOnBackground,
    surface = LightSurface, onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant, onSurfaceVariant = LightOnSurfaceVariant,
    outline = LightOutline
)

private fun purpleDark() = darkColorScheme(
    primary = PurpleDarkPrimary, onPrimary = PurpleDarkOnPrimary,
    primaryContainer = PurpleDarkPrimaryContainer, onPrimaryContainer = PurpleDarkOnPrimaryContainer,
    secondary = PurpleDarkSecondary, onSecondary = PurpleDarkOnSecondary,
    secondaryContainer = PurpleDarkSecondaryContainer, onSecondaryContainer = PurpleDarkOnSecondaryContainer,
    error = CommonDarkError, onError = CommonDarkOnError,
    errorContainer = CommonDarkErrorContainer, onErrorContainer = CommonDarkOnErrorContainer,
    background = DarkBackground, onBackground = DarkOnBackground,
    surface = DarkSurface, onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant, onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOutline
)

private fun greenLight() = lightColorScheme(
    primary = GreenPrimary, onPrimary = GreenOnPrimary,
    primaryContainer = GreenPrimaryContainer, onPrimaryContainer = GreenOnPrimaryContainer,
    secondary = GreenSecondary, onSecondary = GreenOnSecondary,
    secondaryContainer = GreenSecondaryContainer, onSecondaryContainer = GreenOnSecondaryContainer,
    tertiary = GreenTertiary, onTertiary = GreenOnTertiary,
    tertiaryContainer = GreenTertiaryContainer, onTertiaryContainer = GreenOnTertiaryContainer,
    error = CommonError, onError = CommonOnError,
    errorContainer = CommonErrorContainer, onErrorContainer = CommonOnErrorContainer,
    background = LightBackground, onBackground = LightOnBackground,
    surface = LightSurface, onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant, onSurfaceVariant = LightOnSurfaceVariant,
    outline = LightOutline
)

private fun greenDark() = darkColorScheme(
    primary = GreenDarkPrimary, onPrimary = GreenDarkOnPrimary,
    primaryContainer = GreenDarkPrimaryContainer, onPrimaryContainer = GreenDarkOnPrimaryContainer,
    secondary = GreenDarkSecondary, onSecondary = GreenDarkOnSecondary,
    secondaryContainer = GreenDarkSecondaryContainer, onSecondaryContainer = GreenDarkOnSecondaryContainer,
    error = CommonDarkError, onError = CommonDarkOnError,
    errorContainer = CommonDarkErrorContainer, onErrorContainer = CommonDarkOnErrorContainer,
    background = DarkBackground, onBackground = DarkOnBackground,
    surface = DarkSurface, onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant, onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOutline
)

private fun redLight() = lightColorScheme(
    primary = RedPrimary, onPrimary = RedOnPrimary,
    primaryContainer = RedPrimaryContainer, onPrimaryContainer = RedOnPrimaryContainer,
    secondary = RedSecondary, onSecondary = RedOnSecondary,
    secondaryContainer = RedSecondaryContainer, onSecondaryContainer = RedOnSecondaryContainer,
    tertiary = RedTertiary, onTertiary = RedOnTertiary,
    tertiaryContainer = RedTertiaryContainer, onTertiaryContainer = RedOnTertiaryContainer,
    error = CommonError, onError = CommonOnError,
    errorContainer = CommonErrorContainer, onErrorContainer = CommonOnErrorContainer,
    background = LightBackground, onBackground = LightOnBackground,
    surface = LightSurface, onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant, onSurfaceVariant = LightOnSurfaceVariant,
    outline = LightOutline
)

private fun redDark() = darkColorScheme(
    primary = RedDarkPrimary, onPrimary = RedDarkOnPrimary,
    primaryContainer = RedDarkPrimaryContainer, onPrimaryContainer = RedDarkOnPrimaryContainer,
    secondary = RedDarkSecondary, onSecondary = RedDarkOnSecondary,
    secondaryContainer = RedDarkSecondaryContainer, onSecondaryContainer = RedDarkOnSecondaryContainer,
    error = CommonDarkError, onError = CommonDarkOnError,
    errorContainer = CommonDarkErrorContainer, onErrorContainer = CommonDarkOnErrorContainer,
    background = DarkBackground, onBackground = DarkOnBackground,
    surface = DarkSurface, onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant, onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOutline
)

fun getColorScheme(appTheme: AppTheme, isDark: Boolean) = when (appTheme) {
    AppTheme.BLUE -> if (isDark) blueDark() else blueLight()
    AppTheme.PURPLE -> if (isDark) purpleDark() else purpleLight()
    AppTheme.GREEN -> if (isDark) greenDark() else greenLight()
    AppTheme.RED -> if (isDark) redDark() else redLight()
}

@Composable
fun BitacoraDeMaikyTheme(
    appTheme: AppTheme = AppTheme.BLUE,
    darkModeOption: DarkModeOption = DarkModeOption.SYSTEM,
    content: @Composable () -> Unit
) {
    val isDarkTheme = when (darkModeOption) {
        DarkModeOption.SYSTEM -> isSystemInDarkTheme()
        DarkModeOption.LIGHT -> false
        DarkModeOption.DARK -> true
    }

    val colorScheme = getColorScheme(appTheme, isDarkTheme)

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDarkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
