package com.haghpanah.pienote.feature.note

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.eygraber.uri.Uri
import com.haghpanah.pienote.core.component.PienoteChip
import com.haghpanah.pienote.core.component.PienoteScaffold
import com.haghpanah.pienote.core.component.PienoteTextField
import com.haghpanah.pienote.core.navigation.PienoteScreens
import com.haghpanah.pienote.core.texteditor.compose.PienoteTextEditor
import com.haghpanah.pienote.core.texteditor.utils.rememberTextEditorValue
import com.haghpanah.pienote.core.theme.PienoteTheme
import com.haghpanah.pienote.core.utlis.toComposeColor
import com.haghpanah.pienote.feature.note.component.CategoryChipSection
import com.haghpanah.pienote.feature.note.component.ImageCoverSection
import com.haghpanah.pienote.feature.note.component.NoteColorSection
import com.haghpanah.pienote.feature.note.utils.rememberNoteNestedScrollConnection
import com.haghpanh.pienote.commonui.texteditor.compose.TextEditorActionBar
import org.jetbrains.compose.resources.stringResource
import pienote.ui.generated.resources.Res
import pienote.ui.generated.resources.label_untitled

@Composable
actual fun NoteScreen(
    state: NoteViewState,
    parentScreen: String?,
    onImageSelected: (Uri?) -> Unit,
    onUpdateCategory: (Long?) -> Unit,
    onSwitchEditMode: (String, String) -> Unit,
    onUpdateColor: (String?) -> Unit,
    navigateToRoute: (PienoteScreens) -> Unit,
    onBack: (note: String, title: String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val nestedScrollConnection = rememberNoteNestedScrollConnection()
    val noteText = rememberTextEditorValue(initialMarkdown = state.note.markdown)
    var titleText by rememberSaveable { mutableStateOf(state.note.title) }
    var textEditorFocusedItemIndex: Int? by rememberSaveable { mutableStateOf(null) }
    var hasAddedTextEditorSection: Boolean by rememberSaveable { mutableStateOf(false) }

    // Updating note Ui when note has observed successfully from database
    LaunchedEffect(key1 = state.note.title, key2 = state.note.markdown) {
        state.note.title
            .takeIf { it.isNotBlank() }
            ?.let {
                titleText = it
            }

        state.note.markdown
            .takeIf { it.isNotBlank() }
            ?.let {
                noteText.updateMarkdown(it)
            }
    }

    val noteColor: Color by animateColorAsState(
        targetValue = state.note.color?.toComposeColor()
            ?: PienoteTheme.colors.primaryContainer,
        label = "change note color"
    )
    LaunchedEffect(state.isEditing) {
        // we need to check if user switch to reading mode from edit mode or not.
        // in that case we should scroll to top of screen because it may
        // cause some messed up in image section's nested scroll states (alpha, scale,etc.).
        if (!state.isEditing) {
            nestedScrollConnection.reset()
            scrollState.animateScrollTo(
                value = 0,
                animationSpec = tween(300)
            )
        }
    }

    PienoteScaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            if (state.note.color != null) {
                AnimatedVisibility(visible = !state.isEditing) {
                    Box(
                        modifier = Modifier
                            .background(state.note.color!!.toComposeColor())
                            .fillMaxWidth()
                            .height(4.dp)
                    )
                }
            }
        },
        bottomMenu = {
            LaunchedEffect(noteText.getRenderedTexts().size) {
                if (hasAddedTextEditorSection) {
                    focusManager.moveFocus(FocusDirection.Down)
                    hasAddedTextEditorSection = false
                }
            }

            AnimatedVisibility(
                modifier = Modifier.align(Alignment.BottomCenter),
                visible = state.isEditing,
                enter = expandVertically { -it },
                exit = shrinkVertically { -it * 2 }
            ) {
                TextEditorActionBar(
                    modifier = Modifier
                        .widthIn(max = 420.dp),
                    textEditorValue = noteText,
                    textEditorFocusedItemIndex = textEditorFocusedItemIndex,
                    onAddSection = { hasAddedTextEditorSection = true }
                )
            }
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxSize()
                .then(
                    if (!state.isEditing) {
                        Modifier.nestedScroll(nestedScrollConnection)
                    } else {
                        Modifier
                    }
                )
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NoteColorSection(
                selectedColor = state.note.color,
                onUpdateColor = onUpdateColor,
                isEditing = state.isEditing,
                color = noteColor
            )

            if (parentScreen != null) {
                AnimatedVisibility(visible = !state.isEditing) {
                    Row {
                        PienoteChip(
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                            onClick = {
                                onBack(
                                    titleText,
                                    noteText.await().markdown
                                )
                            }
                        ) {
                            Row(
                                modifier = Modifier.padding(6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.ArrowBack,
                                    contentDescription = "back"
                                )

                                Text(
                                    modifier = Modifier.padding(end = 4.dp),
                                    text = parentScreen,
                                    style = PienoteTheme.typography.labelMedium
                                )
                            }
                        }

                        Spacer(Modifier.weight(1f))
                    }
                }
            }

            ImageCoverSection(
                modifier = Modifier
                    .padding(top = 14.dp, start = 24.dp, end = 24.dp)
                    .then(
                        if (!state.isEditing) {
                            Modifier
                                .offset { IntOffset(0, nestedScrollConnection.imageOffset) }
                                .scale(nestedScrollConnection.imageScale)
                                .alpha(nestedScrollConnection.imageAlpha)
                        } else {
                            Modifier
                        }
                    ),
                isEditing = state.isEditing,
                image = state.note.image,
                onClick = {
//                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            )

            Column(
                modifier = Modifier
                    .widthIn(max = 520.dp)
//                    .heightIn(min = localConfig.screenHeightDp.dp - 24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    PienoteTextField(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 14.dp)
                            .onFocusChanged {
                                if (it.isFocused && !state.isEditing) {
                                    onSwitchEditMode(titleText, noteText.await().markdown)
                                }
                            },
                        value = titleText,
                        onValueChange = { titleText = it },
                        placeHolderText = stringResource(Res.string.label_untitled),
                        textStyle = PienoteTheme.typography.displaySmall
                    )

                    PienoteChip(
                        modifier = Modifier
                            .padding(28.dp)
                            .size(42.dp),
                        onClick = {
                            onSwitchEditMode(titleText, noteText.await().markdown)
                        }
                    ) {
                        AnimatedContent(
                            targetState = state.isEditing,
                            label = "switch edit mode",
                        ) { isEditing ->
                            if (isEditing) {
                                Icon(
                                    modifier = Modifier.padding(8.dp),
                                    imageVector = Icons.Rounded.Done,
                                    contentDescription = null
                                )
                            } else {
                                Icon(
                                    modifier = Modifier.padding(10.dp),
                                    imageVector = Icons.Rounded.Edit,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }

                AnimatedVisibility(visible = state.isEditing || state.category != null) {
                    CategoryChipSection(
                        category = state.category,
                        isEditing = state.isEditing,
                        categories = state.categories,
                        onCategorySelect = onUpdateCategory,
                        onClickCategory = { categoryId ->
                            navigateToRoute(
                                PienoteScreens.CategoryScreen(
                                    categoryId,
                                    state.note.title
                                )
                            )
                        }
                    )
                }

                PienoteTextEditor(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxSize()
                        .onFocusChanged {
                            if (it.isFocused && !state.isEditing) {
                                onSwitchEditMode(titleText, noteText.await().markdown)
                            }
                        },
                    value = noteText,
                    shouldShowEditingOptions = state.isEditing,
                    onFocusItemIndexChanged = { textEditorFocusedItemIndex = it }
                )
            }
        }
    }
}