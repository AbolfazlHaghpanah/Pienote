package com.haghpanah.pienote

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.haghpanah.pienote.di.createPienoteModules
import com.haghpanah.pienote.ui.MainScreen
import org.koin.core.context.startKoin

fun main() = application {
    startKoin {
        modules(createPienoteModules())
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Pienote",
    ) {
        MainScreen()
    }
}