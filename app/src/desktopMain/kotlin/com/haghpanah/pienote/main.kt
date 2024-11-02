package com.haghpanah.pienote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.navigation.compose.rememberNavController
import com.haghpanah.pienote.core.component.PienoteChip
import com.haghpanah.pienote.core.navigation.PienoteScreens
import com.haghpanah.pienote.core.theme.PienoteTheme
import com.haghpanah.pienote.di.createPienoteModules
import com.haghpanah.pienote.feature.home.HomeSideBar
import com.haghpanah.pienote.ui.MainScreen
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin
import pienote.ui.generated.resources.Res
import pienote.ui.generated.resources.ic_launcher_foreground
import java.awt.Dimension
import java.awt.Toolkit

fun main() = application {
    startKoin {
        modules(createPienoteModules())
    }

    val windowState = rememberWindowState(
        size = getPreferredWindowSize(1080, 720),
        position = WindowPosition(alignment = Alignment.Center)
    )
    val navController = rememberNavController()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Pienote",
        state = windowState,
        undecorated = true,
        transparent = true,
        icon = painterResource(Res.drawable.ic_launcher_foreground),
    ) {
        var isSideBarVisible by rememberSaveable {
            mutableStateOf(true)
        }

        MenuBar {
            Menu(
                text = "View"
            ) {
                Item(
                    text = "Show Side Bar",
                    enabled = !isSideBarVisible
                ) {
                    isSideBarVisible = true
                }

                Item(
                    text = "Hide Side Bar",
                    enabled = isSideBarVisible
                ) {
                    isSideBarVisible = false
                }

                Item("Enter Full Screen") {
                    windowState.placement = WindowPlacement.Fullscreen
                }

                Item("Minimize") {
                    windowState.placement = WindowPlacement.Maximized
                }
            }

            Menu(
                text = "New"
            ) {
                Item(text = "New Note") {
                    isSideBarVisible = false
                    navController.navigate(
                        PienoteScreens.NoteScreen.createRoute(
                            id = -1,
                            parent = "Home",
                            isExist = false
                        )
                    )
                }

                Item(text = "New Category") {
                    navController.navigate(
                        PienoteScreens.NoteScreen.createRoute(
                            id = -1,
                            parent = "Home",
                            isExist = false
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .clip(PienoteTheme.shapes.large)
                .background(PienoteTheme.colors.surfaceContainerLow)
                .padding(8.dp)
                .fillMaxSize()
        ) {
            HomeSideBar(
                navController = navController,
                visible = isSideBarVisible,
                onChangeVisibility = { isSideBarVisible = it }
            )

            Box(Modifier.clip(PienoteTheme.shapes.large)) {
                MainScreen(navController)
            }
        }
    }
}

private fun getPreferredWindowSize(desiredWidth: Int, desiredHeight: Int): DpSize {
    val screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize
    val preferredWidth: Int = (screenSize.width * 0.8f).toInt()
    val preferredHeight: Int = (screenSize.height * 0.8f).toInt()
    val width: Int = if (desiredWidth < preferredWidth) desiredWidth else preferredWidth
    val height: Int = if (desiredHeight < preferredHeight) desiredHeight else preferredHeight
    return DpSize(width.dp, height.dp)
}