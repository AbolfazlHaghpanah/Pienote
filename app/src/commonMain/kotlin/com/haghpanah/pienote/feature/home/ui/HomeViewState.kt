package com.haghpanah.pienote.feature.home.ui

import androidx.compose.runtime.Immutable
import com.haghpanah.pienote.coreui.model.CategoryUiModel
import com.haghpanah.pienote.coreui.model.NoteUiModel
import com.haghpanh.pienote.commonui.utils.annotation.EffectState


@Immutable
data class HomeViewState(
    val notes: List<NoteUiModel>? = null,
    val categoriesChunked: List<List<CategoryUiModel>>? = null,
    @EffectState val movedToCategoryId: Int? = null,
)