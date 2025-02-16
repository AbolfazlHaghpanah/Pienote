package com.haghpanah.pienote.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            val navController = rememberNavController()

            MainScreen(navController)

            val isDarkTheme = PienoteTheme.isDarkMode
            val background = PienoteTheme.colors.background.toArgb()

            SideEffect {
                enableEdgeToEdge(
                    statusBarStyle = if (isDarkTheme) {
                        SystemBarStyle.dark(Color.Transparent.toArgb())
                    } else {
                        SystemBarStyle.light(
                            scrim = Color.Transparent.toArgb(),
                            darkScrim = Color.Black.toArgb()
                        )
                    },
                    navigationBarStyle = if (isDarkTheme) {
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
    }
}
