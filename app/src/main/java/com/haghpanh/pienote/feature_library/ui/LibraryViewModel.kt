package com.haghpanh.pienote.feature_library.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haghpanh.pienote.feature_library.domain.model.QuickNoteDomainModel
import com.haghpanh.pienote.feature_library.domain.usecase.LibraryInsertQuickNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val libraryInsertQuickNoteUseCase: LibraryInsertQuickNoteUseCase,
) : ViewModel() {
    fun addQuickNote(title: String, note: String) {
        viewModelScope.launch(Dispatchers.IO) {
            libraryInsertQuickNoteUseCase(
                QuickNoteDomainModel(
                    note = note,
                    title = title,
                    addedTime = System.currentTimeMillis().toString()
                )
            )
        }
    }
}