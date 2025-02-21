package com.haghpanah.pienote.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.haghpanah.pienote.designsystem.theme.PienoteTheme
import com.haghpanah.pienote.model.ThemeType
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by inject()
        installSplashScreen()

        setContent {
            val state by viewModel.collectAsStateWithLifecycle()
            val navController = rememberNavController()
            val isSystemDarkTheme = isSystemInDarkTheme()

            val isDarkMode by remember {
                derivedStateOf {
                    when (state.currentTheme) {
                        ThemeType.Dark -> true
                        ThemeType.Light -> false
                        ThemeType.SystemDefault -> isSystemDarkTheme
                        else -> true
                    }
                }
            }

            PienoteTheme(
                isDarkMode = isDarkMode
            ) {
                MainScreen(
                    navController = navController
                )

                SetStatusBarColor(isDarkMode)
            }
        }
    }
}

@Composable
private fun AppCompatActivity.SetStatusBarColor(isDarkMode: Boolean) {
    val background = PienoteTheme.colors.background.toArgb()

    SideEffect {
        enableEdgeToEdge(
            statusBarStyle = if (isDarkMode) {
                SystemBarStyle.dark(Color.Transparent.toArgb())
            } else {
                SystemBarStyle.light(
                    scrim = Color.Transparent.toArgb(),
                    darkScrim = Color.Black.toArgb()
                )
            },
            navigationBarStyle = if (isDarkMode) {
                SystemBarStyle.dark(background)
            } else {
                SystemBarStyle.light(
                    scrim = background,
                    darkScrim = Color.White.toArgb()
                )
            }
        )
    }
}