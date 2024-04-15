package com.haghpanh.pienote.feature_category.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.haghpanh.pienote.common_ui.component.PienoteTopBar
import com.haghpanh.pienote.common_ui.navigation.AppScreens
import com.haghpanh.pienote.common_ui.theme.PienoteTheme
import com.haghpanh.pienote.feature_home.ui.component.HomeNoteItem

@Composable
fun CategoryScreen(
    navController: NavController
) {
    CategoryScreen(
        navController = navController,
        viewModel = hiltViewModel()
    )
}

@Composable
fun CategoryScreen(
    navController: NavController,
    viewModel: CategoryViewModel
) {
    val state by viewModel.collectAsStateWithLifecycle()

    CategoryScreen(
        state = state,
        navigateToRoute = { route -> navController.navigate(route) }
    )
}

@Composable
fun CategoryScreen(
    state: CategoryViewState,
    navigateToRoute: (String) -> Unit
) {
    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .statusBarsPadding()
                .padding(paddingValues)
        ) {
            item {
                Text(
                    modifier = Modifier.padding(32.dp),
                    text = state.name,
                    style = PienoteTheme.typography.h1
                )
            }

            if (state.notes.isNotEmpty()) {
                items(state.notes) {
                    HomeNoteItem(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 14.dp),
                        title = it.title.orEmpty(),
                        note = it.note.orEmpty()
                    ) {
                        navigateToRoute(AppScreens.NoteScreen.createRoute(it.id, true))
                    }
                }
            } else {
                item {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "There is No Note in This Category Yet"
                        )
                    }
                }
            }
        }
    }
}