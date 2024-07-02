package com.haghpanh.pienote.features.noteslist.ui

import com.haghpanh.pienote.features.note.ui.Note
import javax.annotation.concurrent.Immutable

@Immutable
data class NotesListViewState(
    val notes: List<Note> = emptyList()
)
