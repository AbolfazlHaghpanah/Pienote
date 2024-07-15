package com.haghpanh.pienote.features.library.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.haghpanh.pienote.R
import com.haghpanh.pienote.commonui.component.PienoteTopBar
import com.haghpanh.pienote.commonui.navigation.AppScreens
import com.haghpanh.pienote.commonui.theme.PienoteTheme
import com.haghpanh.pienote.features.home.ui.component.QuickNoteTextField

@Composable
fun LibraryScreen(
    navController: NavController
) {
    LibraryScreen(
        navController = navController,
        viewModel = hiltViewModel()
    )
}

@Composable
private fun LibraryScreen(
    navController: NavController,
    viewModel: LibraryViewModel
) {
    LibraryScreen(
        onAddQuickNote = viewModel::addQuickNote
    ) {
        navController.navigate(it) {
            launchSingleTop = true
            popUpTo(it) {
                inclusive = true
            }
        }
    }
}

@Composable
private fun LibraryScreen(
    onAddQuickNote: (String, String) -> Unit,
    navigateToRoute: (String) -> Unit
) {
    var shouldShowQuickNoteTextField by remember {
        mutableStateOf(false)
    }

    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(visible = !shouldShowQuickNoteTextField) {
                FloatingActionButton(
                    onClick = { shouldShowQuickNoteTextField = true },
                    shape = PienoteTheme.shapes.rounded
                ) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .imePadding()
                .padding(top = 14.dp)
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            PienoteTopBar(title = LIBRARY_SCREEN_NAME)

            AnimatedContent(
                targetState = shouldShowQuickNoteTextField,
                label = "expanded quick note",
                transitionSpec = {
                    fadeIn(tween(300))
                        .togetherWith(fadeOut(tween(300)))
                }
            ) {
                if (it) {
                    var quickNoteTitle by remember {
                        mutableStateOf("")
                    }

                    var quickNoteNote by remember {
                        mutableStateOf("")
                    }

                    val resetQuickNoteStates = remember {
                        {
                            shouldShowQuickNoteTextField = false
                            quickNoteNote = ""
                            quickNoteTitle = ""
                        }
                    }

                    QuickNoteTextField(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp),
                        title = quickNoteTitle,
                        note = quickNoteNote,
                        onUpdateTitle = { quickNoteTitle = it },
                        onUpdateNote = { quickNoteNote = it },
                        onDone = {
                            onAddQuickNote(quickNoteTitle, quickNoteNote)
                            resetQuickNoteStates()
                        },
                        onDiscard = {
                            resetQuickNoteStates()
                        }
                    )
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        libsItems.forEach {
                            Divider(Modifier.padding(horizontal = 24.dp))

                            LibsItem(title = it.title, icon = it.iconId) {
                                navigateToRoute(it.route)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Immutable
data class LibsItem(
    val title: String,
    val iconId: Int,
    val route: String
)

const val LIBRARY_SCREEN_NAME = "Library"

val libsItems = listOf(
    LibsItem(
        title = "Home",
        iconId = R.drawable.home,
        route = AppScreens.HomeScreen.createRoute(LIBRARY_SCREEN_NAME)
    ),
    LibsItem(
        title = "Categories",
        iconId = R.drawable.folder,
        route = AppScreens.HomeScreen.createRoute(LIBRARY_SCREEN_NAME)
    ),
    LibsItem(
        title = "Notes",
        iconId = R.drawable.description,
        route = AppScreens.NoteScreen.createRoute(
            id = -1,
            isExist = false,
            parent = LIBRARY_SCREEN_NAME
        )
    ),
    LibsItem(
        title = "Favorite",
        iconId = R.drawable.high_priority,
        route = AppScreens.FavoriteScreen.createRoute(LIBRARY_SCREEN_NAME)
    ),
    LibsItem(
        title = "Notes List",
        iconId = R.drawable.density_medium,
        route = AppScreens.NotesListScreen.createRoute(LIBRARY_SCREEN_NAME)
    )
)

@Composable
fun LibsItem(
    title: String,
    icon: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(PienoteTheme.shapes.medium)
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 6.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(34.dp),
            painter = painterResource(id = icon),
            contentDescription = title
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = title,
            style = PienoteTheme.typography.headlineSmall
        )
    }
}
