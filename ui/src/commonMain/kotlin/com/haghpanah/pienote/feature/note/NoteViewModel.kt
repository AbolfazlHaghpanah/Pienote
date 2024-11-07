package com.haghpanah.pienote.feature.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.eygraber.uri.Uri
import com.haghpanah.pienote.core.utlis.BaseViewModel
import com.haghpanah.pienote.model.emptyNote
import com.haghpanah.pienote.usecase.note.NoteGetCategoriesUseCase
import com.haghpanah.pienote.usecase.note.NoteInsertNoteUseCase
import com.haghpanah.pienote.usecase.note.NoteObserveNoteInfoUseCase
import com.haghpanah.pienote.usecase.note.NoteUpdateNoteImageUseCase
import com.haghpanah.pienote.usecase.note.NoteUpdateNoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(
    private val observeNoteInfoUseCase: NoteObserveNoteInfoUseCase,
    private val getCategoriesUseCase: NoteGetCategoriesUseCase,
    private val insertNoteUseCase: NoteInsertNoteUseCase,
    private val updateNoteUseCase: NoteUpdateNoteUseCase,
    private val noteUpdateNoteImageUseCase: NoteUpdateNoteImageUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<NoteViewState>(
    initialState = NoteViewState(
        isExist = savedStateHandle.get<Boolean>("isExist") ?: false,
        noteId = savedStateHandle.get<Long>("id")
    )
) {
    init {
        getNoteInfo()
        getCategories()
    }

    override fun onCleared() {
        updateOrInsertNote()
        super.onCleared()
    }

    fun switchEditMode(
        currentNoteTitle: String,
        currentNoteMarkdown: String
    ) {
        updateState {
            copy(
                note = getCurrentState().note.copy(
                    markdown = currentNoteMarkdown,
                    title = currentNoteTitle
                )
            )
        }

        if (getCurrentState().isEmptyNote) return

        updateState {
            copy(isEditing = !isEditing)
        }
    }

    fun onNavigateBackRequest(
        currentNoteTitle: String,
        currentNoteMarkdown: String
    ) {
        updateState {
            copy(
                note = getCurrentState().note.copy(
                    markdown = currentNoteMarkdown,
                    title = currentNoteTitle
                )
            )
        }

        if (getCurrentState().isEditing && !getCurrentState().isEmptyNote) {
            switchEditMode(
                currentNoteTitle = currentNoteTitle,
                currentNoteMarkdown = currentNoteMarkdown
            )
        } else if (!getCurrentState().isEmptyNote) {
            updateOrInsertNote()
            updateState { copy(canNavigateBack = true) }
        } else {
            updateState { copy(canNavigateBack = true) }
        }
    }

    fun updateCategory(value: Long?) {
        updateState { copy(note = note.copy(categoryId = value)) }
        updateOrInsertNote()
    }

    private fun updateOrInsertNote() {
        if (getCurrentState().isExist) {
            updateCurrentNote()
        } else {
            insertCurrentNote()
        }
    }

    fun updateNoteImage(uri: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            val note = getCurrentState().note
            val newImage = noteUpdateNoteImageUseCase(note = note, uri = uri?.path)
            val newNote = getCurrentState().note.copy(image = newImage)

            updateState { copy(note = newNote) }
        }
    }

    fun updateNoteText(value: String) {
        updateState {
            val newNote = note.copy(markdown = value)
            copy(note = newNote)
        }
    }

    fun updateNoteColor(value: String?) {
        updateState {
            val newNote = note.copy(color = value)
            copy(note = newNote)
        }
    }

    fun updateTitleText(value: String) {
        updateState {
            val newNote = note.copy(title = value)
            copy(note = newNote)
        }
    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val categories = getCategoriesUseCase()
            updateState { copy(categories = categories) }
        }
    }

    private fun updateCurrentNote() {
        viewModelScope.launch(Dispatchers.IO) {
            updateNoteUseCase(getCurrentState().note)
        }
    }

    private fun insertCurrentNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val noteId = insertNoteUseCase(getCurrentState().note)

            updateState { copy(isExist = true, noteId = noteId) }
            getNoteInfo()
        }
    }

    private fun getNoteInfo() {
        if (getCurrentState().isExist) {
            viewModelScope.launch(Dispatchers.IO) {
                if (getCurrentState().noteId == null) return@launch

                observeNoteInfoUseCase(getCurrentState().noteId!!.toLong()).collect { noteWithCat ->
                    updateState {
                        copy(
                            note = noteWithCat.note,
                            category = noteWithCat.category
                        )
                    }
                }
            }
        } else {
            updateState {
                copy(
                    isEditing = true,
                    note = emptyNote()
                )
            }
        }
    }
}