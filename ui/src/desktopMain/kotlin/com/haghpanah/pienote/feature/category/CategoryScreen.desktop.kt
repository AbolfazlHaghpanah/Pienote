package com.haghpanah.pienote.feature.category

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.toUri
import com.haghpanah.pienote.core.component.PienoteChip
import com.haghpanah.pienote.core.component.PienoteDialog
import com.haghpanah.pienote.core.component.PienoteScaffold
import com.haghpanah.pienote.core.navigation.PienoteScreens
import com.haghpanah.pienote.core.theme.PienoteTheme
import com.haghpanah.pienote.feature.category.component.CATEGORY_DIALOG_ITEM_ADD_NOTE_ID
import com.haghpanah.pienote.feature.category.component.CATEGORY_DIALOG_ITEM_CHANGE_COVER_ID
import com.haghpanah.pienote.feature.category.component.CATEGORY_DIALOG_ITEM_EDIT_NAME_ID
import com.haghpanah.pienote.feature.category.component.CategoryDialogItem
import com.haghpanah.pienote.feature.category.component.CategoryNoteItem
import com.haghpanah.pienote.feature.category.component.DialogState
import com.haghpanah.pienote.feature.category.component.categoryDialogItems
import org.jetbrains.compose.resources.stringResource
import pienote.ui.generated.resources.Res
import pienote.ui.generated.resources.label_discard
import pienote.ui.generated.resources.label_done
import pienote.ui.generated.resources.message_select_note_to_add
import pienote.ui.generated.resources.notes

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal actual fun CategoryScreen(
    state: CategoryViewState,
    parentScreen: String?,
    onDeleteNoteFromCategory: (List<Long>) -> Unit,
    navigateToRoute: (PienoteScreens) -> Unit,
    onBack: () -> Unit,
    onUpdateCategoryName: (String) -> Unit
) {
    var dialogState: DialogState by remember { mutableStateOf(DialogState.Dismiss) }
    val image = state.image ?: state.notes.firstOrNull()?.image
    val selectedNotesId = remember {
        mutableStateListOf<Long>()
    }

    LaunchedEffect(state.notes.size) {
        selectedNotesId.removeAll { selectedNoteId ->
            !state.notes
                .map { note -> note.id }
                .contains(selectedNoteId)
        }
    }

    val dialogItemsAction: (Int) -> Unit = { id ->
        when (id) {
            CATEGORY_DIALOG_ITEM_EDIT_NAME_ID -> {
                dialogState = DialogState.ChangeName
            }

            CATEGORY_DIALOG_ITEM_CHANGE_COVER_ID -> {
//                onRequestToPickMedia()
            }

            CATEGORY_DIALOG_ITEM_ADD_NOTE_ID -> {
                dialogState = DialogState.AddNote
            }
        }
    }

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
                            text = stringResource(Res.string.notes, state.notes.size),
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
                            Text(text = stringResource(Res.string.label_discard))
                        }

                        TextButton(
                            onClick = {
                                onUpdateCategoryName(categoryNameText)
                                dialogState = DialogState.Dismiss
                            }
                        ) {
                            Text(text = stringResource(Res.string.label_done))
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
                            text = stringResource(Res.string.message_select_note_to_add),
                            style = PienoteTheme.typography.headlineSmall
                        )
                    }

                    items(state.availableNotesToAdd) {
                        HorizontalDivider()

                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = it.title,
                            style = PienoteTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }

        else -> {}
    }

    PienoteScaffold(
        snackbarHost = {
//            PienoteSnackbarHost(manager = snackbarManager)
        },
        bottomMenu = {
            AnimatedVisibility(
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.BottomCenter),
                visible = selectedNotesId.isNotEmpty(),
            ) {
                Button(
                    onClick = {
                        //TODO add implementation
                        onDeleteNoteFromCategory(selectedNotesId)
//                        selectedNotesId.removeAll { true }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = null
                    )

                    Text("Remove From Here")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .widthIn(max = 680.dp)
                .align(Alignment.Center)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
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
                                if (!image.isNullOrEmpty()) {
                                    Modifier.aspectRatio(1f)
                                } else {
                                    Modifier.height(180.dp)
                                }
                            )
                    ) {
                        if (image.isNullOrEmpty()) {
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
                            this@Column.AnimatedVisibility(visible = dialogState is DialogState.Dismiss) {
                                PienoteChip(
                                    modifier = Modifier
                                        .statusBarsPadding()
                                        .align(Alignment.TopStart)
                                        .padding(start = 16.dp, top = 16.dp),
                                    onClick = onBack,
                                    backgroundColor = if (!image.isNullOrEmpty()) {
                                        PienoteTheme.colors.background.copy(alpha = 0.3f)
                                    } else {
                                        PienoteTheme.colors.background
                                    },
                                    border = if (!image.isNullOrEmpty()) {
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
                                overflow = TextOverflow.Ellipsis,
                                color = PienoteTheme.colors.onBackground
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
                                backgroundColor = if (!image.isNullOrEmpty()) {
                                    PienoteTheme.colors.background.copy(alpha = 0.3f)
                                } else {
                                    PienoteTheme.colors.background
                                },
                                border = if (!image.isNullOrEmpty()) {
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

            if (state.notes.isNotEmpty()) {
                ContextualFlowRow(
                    itemCount = state.notes.size,
                    maxItemsInEachRow = 3
                ) { index ->
                    runCatching {
                        val note = state.notes[index]
                        val isSelected by rememberSaveable(selectedNotesId.size) {
                            derivedStateOf { selectedNotesId.contains(note.id) }
                        }

                        CategoryNoteItem(
                            modifier = Modifier
                                .padding(14.dp),
                            title = note.title,
                            markdown = note.markdown,
                            color = note.color,
                            onClick = {
                                if (selectedNotesId.isEmpty()) {
                                    navigateToRoute(
                                        PienoteScreens.NoteScreen(
                                            id = note.id,
                                            isExist = true,
                                            parent = state.name
                                        )
                                    )
                                } else {
                                    if (isSelected) {
                                        selectedNotesId.removeAll { it == note.id }
                                    } else {
                                        selectedNotesId.add(note.id)
                                    }
                                }
                            },
                            isSelected = isSelected,
                            onSelectChanged = { newValue ->
                                if (newValue) {
                                    selectedNotesId.add(note.id)
                                } else {
                                    selectedNotesId.removeAll { it == note.id }
                                }
                            }
                        )
                    }.onFailure {
                        println(it.message)
                    }
                }

            } else {
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