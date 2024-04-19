package com.haghpanh.pienote.feature_category.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.common_ui.BaseViewModel
import com.haghpanh.pienote.feature_category.domain.usecase.CategoryObserveCategoryUseCase
import com.haghpanh.pienote.feature_note.ui.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryUseCase: CategoryObserveCategoryUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CategoryViewState>(
    CategoryViewState(
        id = savedStateHandle.get<String>("id")?.toInt() ?: -1
    )
) {
    init {
        getCategoryInfo()
    }

    private fun getCategoryInfo() {
        val category = getCategoryUseCase(getCurrentState().id)

        viewModelScope.launch {
            category.collect { result ->
                updateState { state ->
                    state.copy(
                        name = result.name,
                        priority = result.priority,
                        image = result.image,
                        notes = result.notes.map { it.toUiModel() }
                    )
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
            categoryId = categoryId,
            priority = priority?.toFloat()
        )
}