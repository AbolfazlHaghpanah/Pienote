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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import com.haghpanah.pienote.feature.category.component.DialogState
import com.haghpanah.pienote.feature.category.component.categoryDialogItems
import com.haghpanah.pienote.feature.home.component.HomeNoteItem
import kotlinx.serialization.Contextual
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
    onDeleteNoteFromCategory: (Long) -> Unit,
    navigateToRoute: (String) -> Unit,
    onBack: () -> Unit,
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
        }
    ) { paddingValues ->
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
                                AnimatedVisibility(visible = dialogState is DialogState.Dismiss) {
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
            }

            if (state.notes.isNotEmpty()) {
                item {
                    ContextualFlowRow(
                        itemCount = state.notes.size,
                        maxItemsInEachRow = 3
                    ) {
                        HomeNoteItem(
                            modifier = Modifier
                                .widthIn(200.dp)
                                .heightIn(max = 240.dp)
                                .padding(14.dp),
                            title = state.notes[it].title,
                            note = state.notes[it].markdown,
                            color = state.notes[it].color,
                            onClick = {
                                navigateToRoute(
                                    PienoteScreens.NoteScreen.createRoute(
                                        id = state.notes[it].id.toInt(),
                                        isExist = true,
                                        parent = state.name
                                    )
                                )
                            },
                            isSelected = false,
                            isShowing = false
                        )
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