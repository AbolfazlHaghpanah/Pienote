package com.haghpanh.pienote.note.ui

import androidx.compose.runtime.MutableState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(
        NoteViewState()
    )
    val state = _state.asStateFlow()

    private fun createState() {
        val isExist = savedStateHandle.get<String>("isExist")?.toBoolean() ?: false
        val noteId = savedStateHandle.get<String>("id")?.toInt()

    }
}