package com.haghpanh.pienote.feature_noteslist.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.common_ui.BaseViewModel
import com.haghpanh.pienote.feature_note.ui.Note
import com.haghpanh.pienote.feature_noteslist.domain.usecases.NotesListObserveNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val observeNotesUseCase: NotesListObserveNotesUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<NotesListViewState>(
    initialState = NotesListViewState(),
    savedStateHandle = savedStateHandle
) {

    init {
        getNotes()
    }

    fun getNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            observeNotesUseCase().collect { result ->
                val mappedResult = result.map { it.toUiModel() }

                updateState {
                    it.copy(notes = mappedResult)
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

}