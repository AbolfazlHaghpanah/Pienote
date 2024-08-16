package com.haghpanh.pienote.features.noteslist.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.commonui.BaseViewModel
import com.haghpanh.pienote.features.note.ui.Note
import com.haghpanh.pienote.features.noteslist.domain.repository.NotesListRepository
import com.haghpanh.pienote.features.noteslist.domain.usecases.NotesListObserveNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val observeNotesUseCase: NotesListObserveNotesUseCase,
    savedStateHandle: SavedStateHandle,
    private val notesListRepository: NotesListRepository
) : BaseViewModel<NotesListViewState>(
    initialState = NotesListViewState(),
    savedStateHandle = savedStateHandle
) {

    val gf = notesListRepository
        .getPagedNoteList()
        .cachedIn(viewModelScope)

    init {
        getNotes()
    }

    fun getNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            observeNotesUseCase().collect { result ->
                val mappedResult = result.map { it.toUiModel() }

                updateState { copy(notes = mappedResult) }
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
