package com.haghpanh.pienote.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haghpanh.pienote.commondata.entity.NoteEntity
import com.haghpanh.pienote.home.data.localdatasource.HomeLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeLocalDataSource: HomeLocalDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(createState())
    val state = _state.asStateFlow()

    init {
        observeNotes()
    }

    private fun observeNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            homeLocalDataSource.observeNotes().collect { notes ->
                _state.emit(HomeViewState(notes.map { it.toUiModel() }))
            }
        }
    }

    private fun createState(): HomeViewState =
        HomeViewState(null)

    private fun NoteEntity.toUiModel(): Note =
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