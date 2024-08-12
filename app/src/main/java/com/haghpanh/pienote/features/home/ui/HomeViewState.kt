package com.haghpanh.pienote.features.home.ui

import androidx.compose.runtime.Immutable
import com.haghpanh.pienote.commonui.utils.annotation.EffectState

@Immutable
data class HomeViewState(
    val notes: List<Note>? = null,
    val categoriesChunked: List<List<Category>>? = null,
    @EffectState val movedToCategoryId: Int? = null,
)
