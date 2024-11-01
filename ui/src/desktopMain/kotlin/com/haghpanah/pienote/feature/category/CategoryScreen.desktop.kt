package com.haghpanah.pienote.feature.category

import androidx.compose.runtime.Composable

@Composable
internal actual fun CategoryScreen(
    state: CategoryViewState,
    parentScreen: String?,
    onDeleteNoteFromCategory: (Long) -> Unit,
    navigateToRoute: (String) -> Unit,
    onBack: () -> Unit,
    onUpdateCategoryName: (String) -> Unit
) {
}