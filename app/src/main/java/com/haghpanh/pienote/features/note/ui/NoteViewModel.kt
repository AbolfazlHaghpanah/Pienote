package com.haghpanh.pienote.features.note.ui

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.commonui.BaseViewModel
import com.haghpanh.pienote.features.note.domain.usecase.NoteGetCategoriesUseCase
import com.haghpanh.pienote.features.note.domain.usecase.NoteInsertNoteUseCase
import com.haghpanh.pienote.features.note.domain.usecase.NoteObserveNoteInfoUseCase
import com.haghpanh.pienote.features.note.domain.usecase.NoteUpdateNoteImageUseCase
import com.haghpanh.pienote.features.note.domain.usecase.NoteUpdateNoteUseCase
import com.haghpanh.pienote.features.note.utils.FocusRequestType
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
    savedStateHandle: SavedStateHandle
) : BaseViewModel<NoteViewState>(
    initialState = NoteViewState(
        isExist = savedStateHandle.get<Boolean>("isExist") ?: false,
        noteId = savedStateHandle.get<Int>("id") ?: -1
    ),
    savedStateHandle = savedStateHandle
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

        updateState {
            copy(
                isEditing = !isEditing,
                focusRequestType = focusRequestType
            )
        }
    }

    fun updateCategory(value: Int?) {
        updateState { copy(note = note.copy(categoryId = value)) }
        updateOrInsertNote()
    }

    fun updateOrInsertNote() {
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

            updateState { copy(note = newNote) }
        }
    }

    fun updateNoteText(value: String) {
        updateState {
            val newNote = note.copy(note = value)
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
            val categories = getCategoriesUseCase().map { it.toUiModel() }
            updateState { copy(categories = categories) }
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
            val noteId = insertNoteUseCase(note)

            updateState { copy(isExist = true, noteId = noteId) }
            getNoteInfo()
        }
    }

    private fun getNoteInfo() {
        if (getCurrentState().isExist) {
            viewModelScope.launch(Dispatchers.IO) {
                if (getCurrentState().noteId == null) return@launch

                observeNoteInfoUseCase(getCurrentState().noteId!!).collect { noteWithCat ->
                    updateState {
                        copy(
                            note = noteWithCat.note.toUiModel(),
                            category = noteWithCat.category?.toUiModel()
                        )
                    }
                }
            }
        } else {
            updateState {
                copy(
                    isEditing = true,
                    note = Note()
                )
            }
        }
    }

    fun updateFocusRequester(type: FocusRequestType) {
        updateState {
            copy(focusRequestType = type)
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
