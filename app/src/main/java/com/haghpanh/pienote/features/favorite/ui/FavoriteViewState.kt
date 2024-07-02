package com.haghpanh.pienote.features.favorite.ui

import com.haghpanh.pienote.features.note.ui.Note
import javax.annotation.concurrent.Immutable

@Immutable
data class FavoriteViewState(
    val notes: List<Note> = emptyList()
)
