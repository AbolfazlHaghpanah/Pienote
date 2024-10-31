package com.haghpanah.pienote.feature.home

import androidx.compose.runtime.Immutable
import com.haghpanah.pienote.model.CategoryDomainModel
import com.haghpanah.pienote.model.CategoryWithNotesCountDomainModel
import com.haghpanah.pienote.model.NoteDomainModel
import com.haghpanh.pienote.commonui.utils.annotation.EffectState

@Immutable
data class HomeViewState(
    val notes: List<NoteDomainModel>? = null,
    val categoriesChunked: List<List<CategoryWithNotesCountDomainModel>>? = null,
    @EffectState val movedToCategoryId: Long? = null,
)