package com.haghpanah.pienote.category

import androidx.compose.runtime.Immutable
import com.haghpanah.pienote.model.NoteDomainModel

@Immutable
data class CategoryViewState(
    val id: Long,
    val name: String = "",
    val priority: Int? = null,
    val image: String? = null,
    val notes: List<NoteDomainModel> = emptyList(),
    val availableNotesToAdd: List<NoteDomainModel> = emptyList()
)