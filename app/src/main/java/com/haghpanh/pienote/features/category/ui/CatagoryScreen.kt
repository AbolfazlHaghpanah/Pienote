package com.haghpanh.pienote.features.category.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.haghpanh.pienote.R
import com.haghpanh.pienote.commonui.component.PienoteChip
import com.haghpanh.pienote.commonui.component.PienoteDialog
import com.haghpanh.pienote.commonui.navigation.AppScreens
import com.haghpanh.pienote.commonui.theme.PienoteTheme
import com.haghpanh.pienote.features.category.ui.components.CategoryDialogItem
import com.haghpanh.pienote.features.category.ui.utils.CATEGORY_DIALOG_ITEM_ADD_NOTE_ID
import com.haghpanh.pienote.features.category.ui.utils.CATEGORY_DIALOG_ITEM_CHANGE_COVER_ID
import com.haghpanh.pienote.features.category.ui.utils.CATEGORY_DIALOG_ITEM_EDIT_NAME_ID
import com.haghpanh.pienote.features.category.ui.utils.DialogState
import com.haghpanh.pienote.features.category.ui.utils.categoryDialogItems
import com.haghpanh.pienote.features.home.ui.component.HomeNoteItem

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
    val parentScreen = viewModel.savedStateHandler<String>("parent")

    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = viewModel::updateCategoryImage
    )

    CategoryScreen(
        state = state,
        parentScreen = parentScreen,
        onDeleteNoteFromCategory = viewModel::deleteNoteFromCategory,
        navigateToRoute = { route -> navController.navigate(route) },
        onBack = { navController.popBackStack() },
        onRequestToPickMedia = {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
        onUpdateCategoryName = viewModel::updateCategoryName
    )
}

@Composable
fun CategoryScreen(
    state: CategoryViewState,
    parentScreen: String?,
    onDeleteNoteFromCategory: (Int) -> Unit,
    navigateToRoute: (String) -> Unit,
    onBack: () -> Unit,
    onRequestToPickMedia: () -> Unit,
    onUpdateCategoryName: (String) -> Unit
) {
    var dialogState: DialogState by remember { mutableStateOf(DialogState.Dismiss) }
    val image = state.image ?: state.notes.firstOrNull()?.image

    val dialogItemsAction: (Int) -> Unit = { id ->
        when (id) {
            CATEGORY_DIALOG_ITEM_EDIT_NAME_ID -> {
                dialogState = DialogState.ChangeName
            }

            CATEGORY_DIALOG_ITEM_CHANGE_COVER_ID -> {
                onRequestToPickMedia()
            }

            CATEGORY_DIALOG_ITEM_ADD_NOTE_ID -> {
                dialogState = DialogState.AddNote
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->
        when (dialogState) {
            DialogState.MainDialog -> {
                PienoteDialog(
                    titleSection = {
                        Column {
                            Text(
                                modifier = Modifier
                                    .padding(start = 14.dp)
                                    .fillMaxWidth(),
                                text = state.name,
                                style = PienoteTheme.typography.headlineSmall,
                                color = PienoteTheme.colors.onSurface
                            )

                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 14.dp, vertical = 8.dp)
                                    .fillMaxWidth(),
                                text = stringResource(R.string.notes, state.notes.size),
                                style = PienoteTheme.typography.titleMedium,
                                color = PienoteTheme.colors.onSurface
                            )
                        }
                    },
                    image = state.image?.toUri() ?: state.notes.firstOrNull()?.image?.toUri(),
                    content = {
                        categoryDialogItems.forEach {
                            CategoryDialogItem(
                                title = it.title,
                                icon = it.icon
                            ) {
                                dialogItemsAction(it.id)
                            }
                        }
                    },
                    onDismissRequest = { dialogState = DialogState.Dismiss }
                )
            }

            DialogState.ChangeName -> {
                PienoteDialog(onDismissRequest = { dialogState = DialogState.Dismiss }) {
                    var categoryNameText by remember { mutableStateOf(state.name) }

                    Column {
                        OutlinedTextField(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            value = categoryNameText,
                            onValueChange = { categoryNameText = it },
                            label = { Text(text = "Category Name") }
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            TextButton(
                                onClick = { dialogState = DialogState.Dismiss }
                            ) {
                                Text(text = stringResource(id = R.string.label_discard))
                            }

                            TextButton(
                                onClick = {
                                    onUpdateCategoryName(categoryNameText)
                                    dialogState = DialogState.Dismiss
                                }
                            ) {
                                Text(text = stringResource(id = R.string.label_done))
                            }
                        }
                    }
                }
            }

            DialogState.AddNote -> {
                PienoteDialog(onDismissRequest = { dialogState = DialogState.Dismiss }) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(14.dp)
                            .fillMaxWidth()
                    ) {
                        item {
                            Text(
                                modifier = Modifier.padding(vertical = 8.dp),
                                text = stringResource(R.string.message_select_note_to_add),
                                style = PienoteTheme.typography.headlineSmall
                            )
                        }

                        items(state.availableNotesToAdd) {
                            HorizontalDivider()

                            Text(
                                modifier = Modifier.padding(vertical = 8.dp),
                                text = it.title.orEmpty(),
                                style = PienoteTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }

            else -> {}
        }

        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            item {
                AnimatedContent(
                    targetState = dialogState !is DialogState.Dismiss,
                    label = "on showing dialog screen"
                ) {
                    if (it) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .then(
                                    if (image != null) {
                                        Modifier.aspectRatio(1f)
                                    } else {
                                        Modifier.height(180.dp)
                                    }
                                )
                        ) {
                            if (image != null) {
                                AsyncImage(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    model = state.image ?: state.notes.firstOrNull()?.image,
                                    contentDescription = "image",
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

                            if (parentScreen != null) {
                                AnimatedVisibility(visible = dialogState is DialogState.Dismiss) {
                                    PienoteChip(
                                        modifier = Modifier
                                            .statusBarsPadding()
                                            .align(Alignment.TopStart)
                                            .padding(start = 16.dp, top = 16.dp),
                                        onClick = onBack,
                                        backgroundColor = if (image != null) {
                                            PienoteTheme.colors.background.copy(alpha = 0.3f)
                                        } else {
                                            PienoteTheme.colors.background
                                        },
                                        border = if (image != null) {
                                            null
                                        } else {
                                            BorderStroke(
                                                1.dp,
                                                PienoteTheme.colors.onBackground.copy(alpha = 0.5f)
                                            )
                                        }
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
                                                text = parentScreen,
                                                style = PienoteTheme.typography.labelMedium
                                            )
                                        }
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(32.dp),
                                    text = state.name,
                                    style = PienoteTheme.typography.headlineLarge,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                PienoteChip(
                                    modifier = Modifier
                                        .padding(horizontal = 24.dp)
                                        .size(42.dp)
                                        .aspectRatio(1f),
                                    shape = PienoteTheme.shapes.rounded,
                                    onClick = {
                                        dialogState = DialogState.MainDialog
                                    },
                                    backgroundColor = if (image != null) {
                                        PienoteTheme.colors.background.copy(alpha = 0.3f)
                                    } else {
                                        PienoteTheme.colors.background
                                    },
                                    border = if (image != null) {
                                        null
                                    } else {
                                        BorderStroke(
                                            1.dp,
                                            PienoteTheme.colors.onBackground.copy(alpha = 0.5f)
                                        )
                                    }
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .padding(6.dp)
                                            .fillMaxSize(),
                                        imageVector = Icons.Rounded.MoreVert,
                                        contentDescription = null,
                                        tint = PienoteTheme.colors.onBackground
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if (state.notes.isNotEmpty()) {
                items(state.notes) { note ->
                    HomeNoteItem(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 14.dp),
                        title = note.title.orEmpty(),
                        note = note.note.orEmpty(),
                        onClick = {
                            navigateToRoute(
                                AppScreens.NoteScreen.createRoute(
                                    id = note.id,
                                    isExist = true,
                                    parent = state.name
                                )
                            )
                        },
                        onDelete = {
                            onDeleteNoteFromCategory(note.id)
                        }
                    )
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
