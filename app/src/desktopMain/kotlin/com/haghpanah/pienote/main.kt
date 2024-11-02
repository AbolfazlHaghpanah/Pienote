package com.haghpanah.pienote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.navigation.compose.rememberNavController
import com.haghpanah.pienote.core.theme.PienoteTheme
import com.haghpanah.pienote.di.createPienoteModules
import com.haghpanah.pienote.feature.home.HomeSideBar
import com.haghpanah.pienote.ui.MainScreen
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin
import pienote.ui.generated.resources.Res
import pienote.ui.generated.resources.ic_launcher_foreground

fun main() = application {
    startKoin {
        modules(createPienoteModules())
    }

    val windowState = rememberWindowState()
    val navController = rememberNavController()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Pienote",
        state = windowState,
        icon = painterResource(Res.drawable.ic_launcher_foreground),
    ) {
        Row(
            modifier = Modifier
                .background(PienoteTheme.colors.surfaceContainerLow)
                .fillMaxSize()
        ) {
            HomeSideBar(
                navController = navController
            )

            Box(Modifier.clip(PienoteTheme.shapes.large)){
                MainScreen(navController)
            }
        }
    }
}
