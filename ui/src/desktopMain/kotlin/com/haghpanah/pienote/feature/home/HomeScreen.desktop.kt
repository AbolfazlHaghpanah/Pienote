package com.haghpanah.pienote.feature.home

import androidx.compose.runtime.Composable
import com.haghpanah.pienote.model.NoteDomainModel

@Composable
internal actual fun HomeScreen(
    state: HomeViewState,
    navigateToRoute: (String) -> Unit,
    onDeleteNote: (NoteDomainModel) -> Unit,
    onAddNewCategory: (List<Long>, String, String?) -> Unit,
    onAddNotesToCategory: (noteIds: List<Long>, categoryId: Long) -> Unit
) {

}