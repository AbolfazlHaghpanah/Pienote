package com.haghpanh.pienote.feature_library.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haghpanh.pienote.feature_home.domain.model.QuickNoteDomainModel
import com.haghpanh.pienote.feature_home.domain.usecase.HomeInsertQuickNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val homeInsertQuickNoteUseCase: HomeInsertQuickNoteUseCase,
) : ViewModel() {
    fun addQuickNote(title: String, note: String) {
        viewModelScope.launch(Dispatchers.IO) {
            homeInsertQuickNoteUseCase(
                QuickNoteDomainModel(
                    note = note,
                    title = title,
                    addedTime = System.currentTimeMillis().toString()
                )
            )
        }
    }
}