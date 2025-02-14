package com.haghpanah.pienote.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.eygraber.uri.Uri
import com.haghpanah.pienote.designsystem.component.PienoteScaffold
import com.haghpanah.pienote.designsystem.theme.PienoteTheme
import com.haghpanah.pienote.model.NoteDomainModel
import com.haghpanah.pienote.navigation.PienoteScreens
import com.haghpanah.pienote.snackbar.SnackbarManager
import org.jetbrains.compose.resources.stringResource
import pienote.ui.base.generated.resources.Res
import pienote.ui.base.generated.resources.label_add_note

@Composable
internal actual fun HomeScreen(
    state: HomeViewState,
    snackbarManager: SnackbarManager,
    navigateToRoute: (PienoteScreens) -> Unit,
    onDeleteNote: (NoteDomainModel) -> Unit,
    onAddNewCategory: (List<Long>, String, Uri?) -> Unit,
    onAddNotesToCategory: (noteIds: List<Long>, categoryId: Long) -> Unit
) {
    PienoteScaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    navigateToRoute(
                        PienoteScreens.NoteScreen(
                            id = -1,
                            isExist = false,
                            parent = "Home"
                        )
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add Note"
                    )
                },
                text = {
                    Text(text = stringResource(Res.string.label_add_note))
                },
                expanded = true
            )
        }
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Welcome To Pienote",
            style = PienoteTheme.typography.displaySmall,
            color = PienoteTheme.colors.onBackground
        )
    }
}