package com.haghpanh.pienote.feature_favorite.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.haghpanh.pienote.common_data.utils.toEntity
import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.common_ui.BaseViewModel
import com.haghpanh.pienote.feature_favorite.domain.usecase.FavoriteObserveFavoriteNotesUseCase
import com.haghpanh.pienote.feature_note.ui.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val observeFavoriteNotesUseCase: FavoriteObserveFavoriteNotesUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<FavoriteViewState>(
    initialState = FavoriteViewState(),
    savedStateHandle = savedStateHandle
) {
    init {
        getNotes()
    }

    fun getNotes() {
        viewModelScope.launch(Dispatchers.IO){
            observeFavoriteNotesUseCase().collect{result ->
                updateState {
                    it.copy(notes = result.map { it.toUiModel() })
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