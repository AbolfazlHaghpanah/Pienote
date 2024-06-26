package com.haghpanh.pienote.features.home.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.features.home.domain.usecase.HomeDeleteNoteUseCase
import com.haghpanh.pienote.features.home.domain.usecase.HomeInsertCategoryUseCase
import com.haghpanh.pienote.features.home.domain.usecase.HomeObserveCategories
import com.haghpanh.pienote.features.home.domain.usecase.HomeObserveNotesByCategoryUseCase
import com.haghpanh.pienote.features.home.domain.usecase.HomeObserveNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeObserveNotesUseCase: HomeObserveNotesUseCase,
    private val homeInsertCategoryUseCase: HomeInsertCategoryUseCase,
    private val homeObserveNotesByCategoryUseCase: HomeObserveNotesByCategoryUseCase,
    private val homeObserveCategories: HomeObserveCategories,
    private val homeDeleteNoteUseCase: HomeDeleteNoteUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(HomeViewState())
    val state = _state.asStateFlow()

    private fun getCurrentState() = state.value

    init {
        observeCategories()
        observeNotes()
    }

    fun <T> savedStateHandle(key: String): T? =
        savedStateHandle.get<T>(key)


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
                getCurrentState().copy(
                    hasClickedOnQuickNote = !getCurrentState().hasClickedOnQuickNote,
                    quickNoteNote = null,
                    quickNoteTitle = null
                )
            _state.emit(newState)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            val mappedNote = note.toDomainModel()

            homeDeleteNoteUseCase(mappedNote)
        }
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

    private fun observeCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            homeObserveCategories().collect { categories ->
                val mappedCategories = categories.map { category ->
                    category.toUiModel()
                }
                val newState = getCurrentState().copy(categories = mappedCategories)

                _state.emit(newState)
            }
        }
    }

    private fun CategoryDomainModel.toUiModel(): Category =
        Category(
            id = id,
            name = name,
            priority = priority,
            image = image
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