package com.haghpanah.pienote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.haghpanah.pienote.core.component.DrawerState
import com.haghpanah.pienote.core.component.DrawerState.DrawerValue.Open
import com.haghpanah.pienote.core.component.PienoteDrawer
import com.haghpanah.pienote.core.component.rememberDrawerState
import com.haghpanah.pienote.core.navigation.PienoteScreens
import com.haghpanah.pienote.core.theme.PienoteTheme
import com.haghpanah.pienote.di.createPienoteModules
import com.haghpanah.pienote.feature.home.HomeSideBar
import com.haghpanah.pienote.feature.utils.KeyboardShortcutsManager.addKeyboardShortcut
import com.haghpanah.pienote.feature.utils.KeyboardShortcutsManager.handleKeyEvent
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
        position = WindowPosition.Aligned(Alignment.Center)
    )
    val navController = rememberNavController()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Pienote",
        state = windowState,
        undecorated = true,
        transparent = true,
        onKeyEvent = { keyEvent ->
            when {
                keyEvent.type == KeyEventType.KeyDown -> keyEvent.key.handleKeyEvent()
                else -> false
            }
        },
        icon = painterResource(Res.drawable.ic_launcher_foreground),
    ) {
        val drawerState = rememberDrawerState(Open)

        addKeyboardShortcut(
            Key.Escape
        ) {
            if (drawerState.isOpen) {
                drawerState.close()
            } else {
                drawerState.open()
            }
            false
        }

        PienoteDrawer(
            state = drawerState,
            modifier = Modifier
                .clip(PienoteTheme.shapes.large)
                .background(PienoteTheme.colors.surface)
                .padding(8.dp),
            drawerContent = {
                HomeSideBar(
                    navController = navController,
                    onChangeVisibility = {
                        if (drawerState.isOpen) {
                            drawerState.close()
                        } else {
                            drawerState.open()
                        }
                    }
                )
            }
        ) {
            MainScreen(
                modifier = Modifier.clip(PienoteTheme.shapes.medium),
                navController = navController
            )
        }

        createMenu(
            drawerState = drawerState,
            windowState = windowState,
            navController = navController
        )
    }
}

@Composable
private fun FrameWindowScope.createMenu(
    drawerState: DrawerState,
    windowState: WindowState,
    navController: NavHostController
) {
    MenuBar {
        Menu(
            text = "View"
        ) {
            Item(
                text = "Show Side Bar",
                enabled = drawerState.isClosed
            ) {
                drawerState.close()
            }

            Item(
                text = "Hide Side Bar",
                enabled = drawerState.isOpen
            ) {
                drawerState.open()
            }

            Item("Enter Full Screen") {
                windowState.apply {
                    placement = WindowPlacement.Fullscreen
                }
            }

            Item("Minimize") {
                windowState.placement = WindowPlacement.Maximized
            }
        }

        Menu(
            text = "New"
        ) {
            Item(text = "New Note") {
                navController.navigate(
                    PienoteScreens.NoteScreen(
                        id = -1,
                        parent = "Home",
                        isExist = false
                    )
                ) {
                    popUpTo<PienoteScreens.HomeScreen>()
                }
            }

            Item(text = "New Category") {
                navController.navigate(
                    PienoteScreens.NoteScreen(
                        id = -1,
                        parent = "Home",
                        isExist = false
                    )
                ) {
                    popUpTo<PienoteScreens.HomeScreen>()
                }
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
