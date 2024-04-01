package com.haghpanh.pienote.note.ui

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.note.domain.usecase.NoteGetCategoriesUseCase
import com.haghpanh.pienote.note.domain.usecase.NoteInsertNoteUseCase
import com.haghpanh.pienote.note.domain.usecase.NoteObserveNoteInfoUseCase
import com.haghpanh.pienote.note.domain.usecase.NoteUpdateNoteImageUseCase
import com.haghpanh.pienote.note.domain.usecase.NoteUpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
) : ViewModel() {
    private val isExist = savedStateHandle.get<Boolean>("isExist") ?: false
    private val noteId = savedStateHandle.get<Int>("id") ?: -1

    private val _state = MutableStateFlow(NoteViewState())
    val state = _state.asStateFlow()

    private fun getCurrentState(): NoteViewState = state.value

    init {
        getNoteInfo()
        getCategories()
    }

    fun updateNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val note = getCurrentState().note?.toDomainModel()

            if (note != null) {
                updateNoteUseCase(note)
            }
        }
    }

    fun updateNoteImage(uri: Uri?) {
        viewModelScope.launch {
            val note = getCurrentState().note?.toDomainModel()

            if (note != null) {
                noteUpdateNoteImageUseCase(note, uri)
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val categories = getCategoriesUseCase().map { it.toUiModel() }
            val newState = getCurrentState().copy(categories = categories)

            _state.emit(newState)
        }
    }

    fun updateNoteText(value: String) {
        viewModelScope.launch {
            val newNote = getCurrentState().note?.copy(note = value)
            val newState = getCurrentState().copy(note = newNote)

            _state.emit(newState)
        }
    }

    fun updateTitleText(value: String) {
        viewModelScope.launch {
            val newNote = getCurrentState().note?.copy(title = value)
            val newState = getCurrentState().copy(note = newNote)

            _state.emit(newState)
        }
    }

    private fun getNoteInfo() {
        if (isExist) {
            viewModelScope.launch(Dispatchers.IO) {
                observeNoteInfoUseCase(noteId).collect {
                    val newState = getCurrentState().copy(
                        note = it.note.toUiModel(),
                        category = it.category?.toUiModel()
                    )

                    _state.emit(newState)
                }
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
