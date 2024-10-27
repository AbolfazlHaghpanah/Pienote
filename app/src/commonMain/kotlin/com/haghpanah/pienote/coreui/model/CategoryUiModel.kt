package com.haghpanah.pienote.coreui.model

import androidx.compose.runtime.Immutable

@Immutable
data class CategoryUiModel(
    val id: Int = 0,
    val name: String,
    val priority: Int?,
    val image: String?,
    val noteCounts: Int
)
