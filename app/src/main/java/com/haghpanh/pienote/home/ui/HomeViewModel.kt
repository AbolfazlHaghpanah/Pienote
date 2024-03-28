package com.haghpanh.pienote.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haghpanh.pienote.home.domain.model.NoteDomainModel
import com.haghpanh.pienote.home.domain.model.QuickNoteDomainModel
import com.haghpanh.pienote.home.domain.usecase.HomeInsertQuickNoteUseCase
import com.haghpanh.pienote.home.domain.usecase.HomeObserveNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeInsertQuickNoteUseCase: HomeInsertQuickNoteUseCase,
    private val homeObserveNotesUseCase: HomeObserveNotesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(createState())
    val state = _state.asStateFlow()

    private fun getCurrentState() = state.value

    init {
        observeNotes()
    }

    private fun observeNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            homeObserveNotesUseCase().collect { notes ->
                val mappedNotes = notes.map { it.toUiModel() }
                val newState = getCurrentState().copy(notes = mappedNotes)

                _state.emit(newState)
            }
        }
    }

    fun setQuickNoteTitle(value: String?) {
        viewModelScope.launch {
            val newState = getCurrentState().copy(quickNoteTitle = value)
            _state.emit(newState)
        }
    }

    fun setQuickNoteNote(value: String?) {
        viewModelScope.launch {
            val newState = getCurrentState().copy(quickNoteNote = value)
            _state.emit(newState)
        }
    }

    fun reverseQuickNoteState() {
        viewModelScope.launch {
            val newState =
                getCurrentState().copy(hasClickedOnQuickNote = !getCurrentState().hasClickedOnQuickNote)
            _state.emit(newState)
        }
    }

    fun insertNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            val title = getCurrentState().quickNoteTitle
            val noteText = getCurrentState().quickNoteNote
            val note = QuickNoteDomainModel(
                title = title,
                note = noteText,
                addedTime = Calendar.getInstance().time.toString(),
            )

            homeInsertQuickNoteUseCase(note)

            setQuickNoteTitle(null)
            setQuickNoteNote(null)

            reverseQuickNoteState()
        }
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