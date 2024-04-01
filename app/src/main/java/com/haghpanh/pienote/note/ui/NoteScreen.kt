package com.haghpanh.pienote.note.ui

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.haghpanh.pienote.R
import com.haghpanh.pienote.baseui.theme.PienoteTheme
import com.haghpanh.pienote.note.utils.rememberNoteNestedScrollConnection

@Composable
fun NoteScreen(
    navController: NavController
) {
    NoteScreen(
        navController = navController,
        viewModel = hiltViewModel()
    )
}

@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: NoteViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = viewModel::updateNoteImage
    )

    BackHandler {
        viewModel.updateNote()
        navController.popBackStack()
    }

    DisposableEffect(state) {
        onDispose {
            viewModel.updateNote()
        }
    }

    HomeScreen(
        state = state,
        onUpdateNote = viewModel::updateNoteText,
        onUpdateTitle = viewModel::updateTitleText,
        onRequestToPickImage = {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    )
}

@Composable
fun HomeScreen(
    state: NoteViewState,
    onUpdateNote: (String) -> Unit,
    onUpdateTitle: (String) -> Unit,
    onRequestToPickImage: () -> Unit
) {
    val scrollState = rememberScrollState()
    val nestedScrollConnection = rememberNoteNestedScrollConnection()

    Scaffold(
        modifier = Modifier.imePadding()
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
                .verticalScroll(scrollState)
        ) {
            state.note?.image?.let { imageUri ->
                Box(
                    modifier = Modifier
                        .statusBarsPadding()
                        .offset { IntOffset(0, nestedScrollConnection.imageOffset) }
                        .scale(nestedScrollConnection.imageScale)
                        .alpha(nestedScrollConnection.imageAlpha)
                        .clip(PienoteTheme.shapes.large)
                        .clickable(onClick = onRequestToPickImage)
                        .fillMaxWidth()
                        .aspectRatio(1.6f)
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = imageUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        PienoteTheme.colors.background
                                    )
                                )
                            )
                    )
                }
            } ?: Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(5f)
                    .alpha(nestedScrollConnection.imageAlpha)
            ) {
                TextButton(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = onRequestToPickImage,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = PienoteTheme.colors.onBackground.copy(alpha = 0.3f)
                    )
                ) {
                    Text(text = stringResource(R.string.add_cover_image))
                }
            }

            Column {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = state.note?.title.orEmpty(),
                    onValueChange = onUpdateTitle,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.label_untitled),
                            style = PienoteTheme.typography.h1
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    textStyle = PienoteTheme.typography.h1
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 250.dp),
                    value = state.note?.note.orEmpty(),
                    onValueChange = onUpdateNote,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.label_write_here),
                            style = PienoteTheme.typography.body2
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    textStyle = PienoteTheme.typography.body1
                )

                Spacer(modifier = Modifier.height(200.dp))
            }
        }
    }
}