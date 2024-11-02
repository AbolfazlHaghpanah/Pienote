package com.haghpanah.pienote.feature.home

import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.haghpanah.pienote.core.component.PienoteChip
import com.haghpanah.pienote.core.navigation.PienoteScreens
import com.haghpanah.pienote.core.theme.PienoteTheme
import com.haghpanah.pienote.feature.home.component.HomeCategoryItem
import com.haghpanah.pienote.feature.home.component.HomeNoteItem
import com.haghpanah.pienote.model.NoteDomainModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import pienote.ui.generated.resources.Res
import pienote.ui.generated.resources.label_home

@Composable
fun HomeSideBar(
    navController: NavController
) {
    HomeSideBar(
        navController = navController,
        viewModel = koinViewModel()
    )
}

@Composable
private fun HomeSideBar(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val state by viewModel.collectAsStateWithLifecycle()

    viewModel.handleEffectsDispose()

    HomeSideBar(
        state = state,
        navigateToRoute = { route ->
            navController.navigate(route = route) {
                popUpTo(route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        },
        onDeleteNote = viewModel::deleteNote,
        onAddNewCategory = viewModel::addNewCategory,
        onAddNotesToCategory = viewModel::addNoteToCategory
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeSideBar(
    state: HomeViewState,
    navigateToRoute: (String) -> Unit,
    onDeleteNote: (NoteDomainModel) -> Unit,
    onAddNewCategory: (List<Long>, String, String?) -> Unit,
    onAddNotesToCategory: (noteIds: List<Long>, categoryId: Long) -> Unit
) {
    val selectedNotes = remember { mutableStateListOf<NoteDomainModel>() }
    val isSelectingNote by remember {
        derivedStateOf { selectedNotes.isNotEmpty() }
    }

    LazyColumn(
        modifier = Modifier.widthIn(max = 300.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = PienoteTheme.colors.onBackground
                ),
                title = {
                    Text(
                        text = stringResource(Res.string.label_home),
                        style = PienoteTheme.typography.headlineSmall,
                        color = PienoteTheme.colors.onBackground
                    )
                },
                actions = {
                    PienoteChip(
                        backgroundColor = Color.Transparent,
                        onClick = {
                            navigateToRoute(
                                PienoteScreens.NoteScreen.createRoute(
                                    id = -1,
                                    isExist = false,
                                    parent = "Home",
                                )
                            )
                        },
                        content = {
                            Icon(
                                modifier = Modifier.padding(4.dp),
                                imageVector = Icons.Rounded.Add,
                                contentDescription = null,
                                tint = PienoteTheme.colors.onBackground
                            )
                        }
                    )
                },
                scrollBehavior = null,
            )
        }

        items(
            items = state.categoriesChunked?.flatten() ?: emptyList(),
            key = { item -> item.id.hashCode() }
        ) { category ->
            Modifier.padding(horizontal = 24.dp)

            HomeCategoryItem(
                modifier = Modifier.fillMaxWidth(),
                name = category.name,
                image = category.image,
                noteCount = category.noteCount
            ) {
                navigateToRoute(
                    PienoteScreens.CategoryScreen.createRoute(
                        category.id.toInt(),
                        parent = "Home"
                    )
                )
            }
        }

        items(
            items = state.notes ?: emptyList(),
            key = { item -> "${item.id}_${item.title}" }
        ) { note ->
            val isNoteSelected by remember(selectedNotes.size) {
                derivedStateOf { selectedNotes.contains(note) }
            }

            HomeNoteItem(
                modifier = Modifier
                    .animateItem(
                        fadeInSpec = tween(),
                        fadeOutSpec = tween(),
                        placementSpec = spring()
                    ),
                title = note.title,
                note = note.markdown,
                color = note.color,
                isSelected = isNoteSelected,
                onClick = {
                    if (isSelectingNote) {
                        if (selectedNotes.contains(note)) {
                            selectedNotes.remove(note)
                        } else {
                            selectedNotes.add(note)
                        }
                    } else {
                        navigateToRoute(
                            PienoteScreens.NoteScreen.createRoute(
                                id = note.id.toInt(),
                                isExist = true,
                                parent = "Home"
                            )
                        )
                    }
                },
                onLongClick = {
                    selectedNotes.add(note)
                }
            )
        }
    }
}