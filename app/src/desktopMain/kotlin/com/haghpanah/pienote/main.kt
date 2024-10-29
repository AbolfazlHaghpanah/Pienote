package com.haghpanah.pienote

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.haghpanah.pienote.di.databaseModule
import com.haghpanah.pienote.ui.MainScreen
import org.koin.core.context.startKoin

fun main() = application {
    startKoin {
        modules(
            databaseModule
        )
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Pienote",
    ) {
        MainScreen()
    }
}