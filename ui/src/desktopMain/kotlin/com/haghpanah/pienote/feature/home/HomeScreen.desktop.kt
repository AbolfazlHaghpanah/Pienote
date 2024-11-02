package com.haghpanah.pienote.feature.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.haghpanah.pienote.core.component.PienoteScaffold
import com.haghpanah.pienote.core.navigation.PienoteScreens
import com.haghpanah.pienote.core.theme.PienoteTheme
import com.haghpanah.pienote.model.NoteDomainModel
import org.jetbrains.compose.resources.stringResource
import pienote.ui.generated.resources.Res
import pienote.ui.generated.resources.label_add_note

@Composable
internal actual fun HomeScreen(
    state: HomeViewState,
    navigateToRoute: (String) -> Unit,
    onDeleteNote: (NoteDomainModel) -> Unit,
    onAddNewCategory: (List<Long>, String, String?) -> Unit,
    onAddNotesToCategory: (noteIds: List<Long>, categoryId: Long) -> Unit
) {
    PienoteScaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    navigateToRoute(
                        PienoteScreens.NoteScreen.createRoute(
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