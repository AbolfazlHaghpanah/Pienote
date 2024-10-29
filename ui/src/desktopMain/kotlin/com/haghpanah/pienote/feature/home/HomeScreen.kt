package com.haghpanah.pienote.feature.home

import androidx.compose.runtime.Composable
import com.haghpanah.pienote.domain.model.NoteDomainModel

@Composable
internal actual fun HomeScreen(
    state: HomeViewState,
    navigateToRoute: (String) -> Unit,
    onDeleteNote: (NoteDomainModel) -> Unit,
    onAddNewCategory: (List<Int>, String, String) -> Unit,
    onAddNotesToCategory: (noteIds: List<Int>, categoryId: Int) -> Unit
) {

}