package com.haghpanah.pienote.home

import androidx.compose.runtime.Immutable
import com.haghpanah.pienote.baseui.annotation.EffectState
import com.haghpanah.pienote.model.CategoryWithNotesCountDomainModel
import com.haghpanah.pienote.model.NoteDomainModel

@Immutable
data class HomeViewState(
    val notes: List<NoteDomainModel>? = null,
    val categoriesChunked: List<List<CategoryWithNotesCountDomainModel>>? = null,
    @EffectState val movedToCategoryId: Long? = null,
)