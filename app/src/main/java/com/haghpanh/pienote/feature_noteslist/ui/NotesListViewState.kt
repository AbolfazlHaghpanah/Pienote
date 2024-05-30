package com.haghpanh.pienote.feature_noteslist.ui

import com.haghpanh.pienote.feature_note.ui.Note
import javax.annotation.concurrent.Immutable

@Immutable
data class NotesListViewState(
    val notes : List<Note> = emptyList()
)