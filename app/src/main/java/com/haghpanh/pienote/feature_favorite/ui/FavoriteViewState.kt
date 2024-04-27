package com.haghpanh.pienote.feature_favorite.ui

import com.haghpanh.pienote.feature_note.ui.Note
import javax.annotation.concurrent.Immutable

@Immutable
data class FavoriteViewState(
    val notes : List<Note> = emptyList()
)
