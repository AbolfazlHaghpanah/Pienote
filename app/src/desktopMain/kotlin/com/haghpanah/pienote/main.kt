package com.haghpanah.pienote

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.haghpanah.pienote.coreui.main.MainScreen
import com.haghpanah.pienote.feature.home.di.homeModule
import org.koin.core.context.startKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Pienote",
    ) {
        startKoin {
            homeModule
        }

        MainScreen()
    }
}