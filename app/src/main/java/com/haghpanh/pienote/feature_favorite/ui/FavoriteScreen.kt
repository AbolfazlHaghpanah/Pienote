package com.haghpanh.pienote.feature_favorite.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.haghpanh.pienote.common_ui.component.PienoteChip
import com.haghpanh.pienote.common_ui.component.PienoteTopBar
import com.haghpanh.pienote.common_ui.navigation.AppScreens
import com.haghpanh.pienote.common_ui.theme.PienoteTheme
import com.haghpanh.pienote.feature_home.ui.component.HomeNoteItem

@Composable
fun FavoriteScreen(
    navController: NavController
) {
    FavoriteScreen(
        navController = navController,
        viewModel = hiltViewModel()
    )
}

@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: FavoriteViewModel
) {
    val state by viewModel.collectAsStateWithLifecycle()

    FavoriteScreen(
        state = state,
        parentScreenName = viewModel.savedStateHandler<String>("parent"),
        onBack = { navController.popBackStack() },
        navigateToRoute = { navController.navigate(it) }
    )
}

@Composable
fun FavoriteScreen(
    state: FavoriteViewState,
    parentScreenName: String?,
    navigateToRoute: (String) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.statusBarsPadding()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Column {
                    if (parentScreenName != null) {
                        PienoteChip(
                            modifier = Modifier.padding(start = 18.dp, top = 16.dp, bottom = 6.dp),
                            onClick = onBack
                        ) {
                            Row(
                                modifier = Modifier.padding(6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                    contentDescription = "back"
                                )

                                Text(
                                    modifier = Modifier.padding(end = 4.dp),
                                    text = parentScreenName,
                                    style = PienoteTheme.typography.subtitle1
                                )
                            }
                        }
                    }

                    PienoteTopBar(title = "Favorites")
                }
            }

            items(state.notes) { note ->
                HomeNoteItem(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    title = note.title.orEmpty(),
                    note = note.note.orEmpty(),
                    onDelete = {}
                ) {
                    navigateToRoute(AppScreens.NoteScreen.createRoute(note.id, true, "Favorite"))
                }
            }
        }
    }
}