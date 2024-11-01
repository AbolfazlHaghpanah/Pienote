package com.haghpanah.pienote.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.haghpanah.pienote.model.NoteDomainModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeScreen(
    navController: NavController
) {
    HomeScreen(
        navController = navController,
        viewModel = koinViewModel()
    )
}

@Composable
private fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val state by viewModel.collectAsStateWithLifecycle()

    viewModel.handleEffectsDispose()

    HomeScreen(
        state = state,
//        snackbarManager = viewModel.snackbarManager,
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

@Composable
internal expect fun HomeScreen(
    state: HomeViewState,
//    snackbarManager: SnackbarManager,
    navigateToRoute: (String) -> Unit,
    onDeleteNote: (NoteDomainModel) -> Unit,
    onAddNewCategory: (List<Long>, String, String?) -> Unit,
    onAddNotesToCategory: (noteIds: List<Long>, categoryId: Long) -> Unit
)