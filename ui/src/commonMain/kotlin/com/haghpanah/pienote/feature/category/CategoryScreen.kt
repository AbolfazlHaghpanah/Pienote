package com.haghpanah.pienote.feature.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.haghpanah.pienote.core.navigation.PienoteScreens
import com.haghpanah.pienote.core.utlis.SnackbarManager
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CategoryScreen(
    navController: NavController
) {
    CategoryScreen(
        navController = navController,
        viewModel = koinViewModel()
    )
}

@Composable
private fun CategoryScreen(
    navController: NavController,
    viewModel: CategoryViewModel
) {
    val state by viewModel.collectAsStateWithLifecycle()
    val parentScreen = navController.currentBackStackEntry?.arguments?.getString("parent")

    CategoryScreen(
        state = state,
        parentScreen = parentScreen,
        snackbarManager = viewModel::snackbarManager.get(),
        onDeleteNoteFromCategory = viewModel::deleteNoteFromCategory,
        navigateToRoute = { route -> navController.navigate(route) },
        onBack = { navController.popBackStack() },
        onUpdateCategoryName = viewModel::updateCategoryName
    )
}

@Composable
internal expect fun CategoryScreen(
    state: CategoryViewState,
    parentScreen: String?,
    snackbarManager: SnackbarManager,
    onDeleteNoteFromCategory: (List<Long>) -> Unit,
    navigateToRoute: (PienoteScreens) -> Unit,
    onBack: () -> Unit,
    onUpdateCategoryName: (String) -> Unit
)
