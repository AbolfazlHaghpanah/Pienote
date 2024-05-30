package com.haghpanh.pienote.feature_category.ui

import com.haghpanh.pienote.feature_note.ui.Note
import javax.annotation.concurrent.Immutable

@Immutable
data class CategoryViewState(
    val id: Int,
    val name: String = "",
    val priority: Int? = null,
    val image: String? = null,
    val notes: List<Note> = emptyList(),
    val availableNotesToAdd: List<Note> = emptyList()
)
