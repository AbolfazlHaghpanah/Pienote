package com.haghpanh.pienote.note.ui

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.haghpanh.pienote.baseui.BaseViewModel
import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.note.domain.usecase.NoteGetCategoriesUseCase
import com.haghpanh.pienote.note.domain.usecase.NoteInsertNoteUseCase
import com.haghpanh.pienote.note.domain.usecase.NoteObserveNoteInfoUseCase
import com.haghpanh.pienote.note.domain.usecase.NoteUpdateNoteImageUseCase
import com.haghpanh.pienote.note.domain.usecase.NoteUpdateNoteUseCase
import com.haghpanh.pienote.note.utils.FocusRequestType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val observeNoteInfoUseCase: NoteObserveNoteInfoUseCase,
    private val getCategoriesUseCase: NoteGetCategoriesUseCase,
    private val insertNoteUseCase: NoteInsertNoteUseCase,
    private val updateNoteUseCase: NoteUpdateNoteUseCase,
    private val noteUpdateNoteImageUseCase: NoteUpdateNoteImageUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<NoteViewState>(
    NoteViewState(
        isExist = savedStateHandle.get<Boolean>("isExist") ?: false
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

    fun switchEditMode(focusRequestType: FocusRequestType = FocusRequestType.Non) {
        if (getCurrentState().isEmptyNote) return

        updateState { state ->
            state.copy(
                isEditing = !state.isEditing,
                focusRequestType = focusRequestType
            )
        }
    }

    fun updateCategory(value: Int?) {
        updateState(
            onUpdated = ::updateOrInsertNote
        ) { state ->
            val newNote = state.note.copy(categoryId = value)
            state.copy(note = newNote)
        }
    }

    fun updateOrInsertNote() {
        if (getCurrentState().isEmptyNote) return

        if (getCurrentState().isExist) {
            updateCurrentNote()
        } else {
            insertCurrentNote()
        }
    }

    fun updateNoteImage(uri: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            val note = getCurrentState().note.toDomainModel()
            val newImage = noteUpdateNoteImageUseCase(note = note, uri = uri)
            val newNote = getCurrentState().note.copy(image = newImage?.toString())

            updateState { state ->
                state.copy(note = newNote)
            }
        }
    }

    fun updateNoteText(value: String) {
        updateState { state ->
            val newNote = getCurrentState().note.copy(note = value)
            state.copy(note = newNote)
        }
    }

    fun updateTitleText(value: String) {
        updateState {
            val newNote = getCurrentState().note.copy(title = value)
            it.copy(note = newNote)
        }
    }

    private fun getCategories() {
        updateState { state ->
            val categories = getCategoriesUseCase().map { it.toUiModel() }
            state.copy(categories = categories)
        }
    }

    private fun updateCurrentNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val note = getCurrentState().note.toDomainModel()

            updateNoteUseCase(note)
        }
    }

    private fun insertCurrentNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val note = getCurrentState().note.toDomainModel()

            insertNoteUseCase(note)
        }
    }

    private fun getNoteInfo() {
        if (getCurrentState().isExist) {
            viewModelScope.launch(Dispatchers.IO) {
                val noteId = savedStateHandle.get<Int>("id") ?: -1

                observeNoteInfoUseCase(noteId).collect { noteWithCat ->
                    updateState { state ->
                        state.copy(
                            note = noteWithCat.note.toUiModel(),
                            category = noteWithCat.category?.toUiModel()
                        )
                    }
                }
            }
        } else {
            updateState { state ->
                state.copy(
                    isEditing = true,
                    note = Note()
                )
            }
        }
    }

    private fun NoteDomainModel.toUiModel(): Note =
        Note(
            id = id,
            title = title,
            note = note,
            image = image,
            addedTime = addedTime,
            lastChangedTime = lastChangedTime,
            categoryId = categoryId
        )

    private fun CategoryDomainModel.toUiModel(): Category =
        Category(
            id = id,
            name = name,
            priority = priority,
            image = image
        )
}
