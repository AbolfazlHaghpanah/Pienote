package com.haghpanh.pienote.home.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.haghpanh.pienote.baseui.theme.PienoteTheme

@Composable
fun HomeScreen(
    navController: NavController
) {
    HomeScreen(
        navController = navController,
        viewModel = hiltViewModel()
    )
}

@Composable
private fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(state = state)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    state: HomeViewState
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            LazyColumn {
                items(
                    items = state.notes ?: emptyList(),
                    key = { item -> item.id }
                ) { note ->
                    HomeNoteItem(
                        modifier = Modifier.animateItemPlacement(),
                        title = note.title,
                        note = note.note
                    )
                }
            }
        }
    }
}

@Composable
fun HomeNoteItem(
    title: String,
    note: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(
                top = 24.dp,
                start = 24.dp,
                end = 24.dp
            )
            .clip(PienoteTheme.shapes.veryLarge)
            .background(
                color = PienoteTheme.colors.surface,
                shape = PienoteTheme.shapes.veryLarge
            )
            .fillMaxWidth()
            .aspectRatio(2.4f)
    ) {
        Text(
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp),
            text = title,
            style = PienoteTheme.typography.h6,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = PienoteTheme.colors.onSurface
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp),
            text = note,
            style = PienoteTheme.typography.body2,
            overflow = TextOverflow.Ellipsis,
            color = PienoteTheme.colors.onSurface
        )
    }
}