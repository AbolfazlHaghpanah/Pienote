package com.haghpanh.pienote.feature_category.ui

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.haghpanh.pienote.common_domain.model.CategoryDomainModel
import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.common_ui.BaseViewModel
import com.haghpanh.pienote.feature_category.domain.usecase.CategoryDeleteNoteFromCategoryUseCase
import com.haghpanh.pienote.feature_category.domain.usecase.CategoryObserveCategoryUseCase
import com.haghpanh.pienote.feature_category.domain.usecase.CategoryUpdateCategoryUseCase
import com.haghpanh.pienote.feature_category.domain.usecase.CategoryUpdateImageUseCase
import com.haghpanh.pienote.feature_note.ui.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryUseCase: CategoryObserveCategoryUseCase,
    private val updateCategoryUseCase: CategoryUpdateCategoryUseCase,
    private val deleteNoteFromCategoryUseCase: CategoryDeleteNoteFromCategoryUseCase,
    private val updateImageUseCase: CategoryUpdateImageUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CategoryViewState>(
    initialState = CategoryViewState(
        id = savedStateHandle.get<Int>("id") ?: -1
    ),
    savedStateHandle = savedStateHandle
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

    fun deleteNoteFromCategory(noteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteNoteFromCategoryUseCase(noteId)
        }
    }

    fun updateCategoryImage(uri: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentState = getCurrentState()
            val currentCategory = CategoryDomainModel(
                id = currentState.id,
                name = currentState.name,
                priority = currentState.priority,
                image = currentState.image
            )

            updateImageUseCase(
                currentCategory = currentCategory,
                uri = uri
            )
        }
    }

    fun updateCategoryName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentState = getCurrentState()
            val newCategory = CategoryDomainModel(
                id = currentState.id,
                name = name,
                priority = currentState.priority,
                image = currentState.image
            )

            updateCategoryUseCase(newCategory)
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