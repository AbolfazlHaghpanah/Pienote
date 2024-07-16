package com.haghpanh.pienote.features.library.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.haghpanh.pienote.R
import com.haghpanh.pienote.commonui.component.PienoteTopBar
import com.haghpanh.pienote.features.library.ui.components.QuickNoteTextField
import com.haghpanh.pienote.features.library.ui.components.LibsItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val LIBRARY_SCREEN_NAME = "Library"

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
    var shouldExpandFAB by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        launch {
            delay(2000)
            shouldExpandFAB = true
            delay(3000)
            shouldExpandFAB = false
        }
    }

    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(visible = !shouldShowQuickNoteTextField) {
                ExtendedFloatingActionButton(
                    text = {
                        Text(
                            text = stringResource(id = R.string.label_quick_note)
                        )
                    },
                    icon = {
                        Icon(imageVector = Icons.Rounded.Add, contentDescription = "")
                    },
                    expanded = shouldExpandFAB,
                    onClick = { shouldShowQuickNoteTextField = true }
                )
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
            ) { showQuickNote ->
                if (showQuickNote) {
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
                        libsItems.forEach { item ->
                            HorizontalDivider(Modifier.padding(horizontal = 24.dp))

                            LibsItem(
                                title = item.title,
                                icon = item.iconId,
                                onClick = { navigateToRoute(item.route) }
                            )
                        }
                    }
                }
            }
        }
    }
}
