package com.haghpanh.pienote.features.home.ui.component

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.commonui.theme.PienoteTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectNoteSheetContent(
    state: BottomSheetScaffoldState,
    modifier: Modifier = Modifier,
    shouldDisableContentGesture: Boolean = false,
    partiallyExpandedContent: @Composable () -> Unit,
    expandedContent: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        modifier = modifier,
        sheetContainerColor = PienoteTheme.colors.surfaceContainerLowest,
        sheetContentColor = PienoteTheme.colors.onSurface,
        scaffoldState = state,
        sheetPeekHeight = 64.dp,
        containerColor = PienoteTheme.colors.background,
        sheetDragHandle = null,
        sheetSwipeEnabled = false,
        sheetContent = {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                partiallyExpandedContent()

                expandedContent()
            }
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                content(it)

                if (shouldDisableContentGesture) {
                    Box(
                        modifier = Modifier
                            .pointerInput(Unit) {
                                detectTapGestures {
                                    scope.launch {
                                        state.bottomSheetState.partialExpand()
                                    }
                                }
                            }
                            .fillMaxSize()
                    )
                }
            }
        }
    )
}

enum class SelectBottomSheetOptions(
    val label: String,
    val icon: ImageVector? = null,
    val color: Color? = null
) {
    MoveToCategory("Move To Category"),
    AddToFavorite("Add To Favorite"),
    DeleteNotes("Delete Notes")
}
