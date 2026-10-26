package com.puregold.financetracker.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Emerald40,
    onPrimary = Color.White,
    primaryContainer = EmeraldContainer40,
    onPrimaryContainer = OnEmeraldContainer40,
    secondary = Slate40,
    onSecondary = Color.White,
    secondaryContainer = SlateContainer40,
    onSecondaryContainer = OnSlateContainer40,
    tertiary = Gold40,
)

private val DarkColorScheme = darkColorScheme(
    primary = Emerald80,
    onPrimary = Color(0xFF003827),
    primaryContainer = EmeraldContainer80,
    onPrimaryContainer = OnEmeraldContainer80,
    secondary = Slate80,
    onSecondary = Color(0xFF1E352D),
    secondaryContainer = SlateContainer80,
    onSecondaryContainer = OnSlateContainer80,
    tertiary = Gold80,
)

@Composable
fun FinanceTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Off by default so the brand palette is used; set true to follow the wallpaper on Android 12+.
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
