package com.haghpanah.pienote.feature.category

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.haghpanah.pienote.feature.category.component.CATEGORY_DIALOG_ITEM_ADD_NOTE_ID
import com.haghpanah.pienote.feature.category.component.CATEGORY_DIALOG_ITEM_CHANGE_COVER_ID
import com.haghpanah.pienote.feature.category.component.CATEGORY_DIALOG_ITEM_EDIT_NAME_ID
import com.haghpanah.pienote.feature.category.component.DialogState
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
//        snackbarManager = viewModel::snackbarManager.get(),
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
//    snackbarManager: SnackbarManager,
    onDeleteNoteFromCategory: (Long) -> Unit,
    navigateToRoute: (String) -> Unit,
    onBack: () -> Unit,
    onUpdateCategoryName: (String) -> Unit
)
