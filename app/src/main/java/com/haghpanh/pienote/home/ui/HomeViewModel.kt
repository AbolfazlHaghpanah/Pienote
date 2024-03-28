package com.haghpanh.pienote.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haghpanh.pienote.home.domain.model.NoteDomainModel
import com.haghpanh.pienote.home.domain.usecase.HomeInsertNoteUseCase
import com.haghpanh.pienote.home.domain.usecase.HomeObserveNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeInsertNoteUseCase: HomeInsertNoteUseCase,
    private val homeObserveNotesUseCase: HomeObserveNotesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(createState())
    val state = _state.asStateFlow()

    init {
        observeNotes()
    }

    private fun observeNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            homeObserveNotesUseCase().collect { notes ->
                val mappedNotes = notes.map { it.toUiModel() }
                val newState = state.value.copy(notes = mappedNotes)

                _state.emit(newState)
            }
        }
    }

    fun insertNotes() {

    }

    private fun createState(): HomeViewState =
        HomeViewState(
            notes = null,
            quickNoteTitle = null,
            quickNoteNote = null
        )

    private fun NoteDomainModel.toUiModel(): Note =
        Note(
            id = id,
            title = title,
            note = note,
            image = image,
            addedTime = addedTime,
            lastChangedTime = lastChangedTime,
            categoryId = categoryId,
            priority = priority
        )
}