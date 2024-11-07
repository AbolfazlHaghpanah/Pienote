package com.haghpanah.pienote.feature.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.eygraber.uri.Uri
import com.haghpanah.pienote.core.utlis.BaseViewModel
import com.haghpanah.pienote.model.CategoryDomainModel
import com.haghpanah.pienote.usecase.category.CategoryAddNoteToCategoryUseCase
import com.haghpanah.pienote.usecase.category.CategoryDeleteNoteFromCategoryUseCase
import com.haghpanah.pienote.usecase.category.CategoryObserveAvailableNotesUseCase
import com.haghpanah.pienote.usecase.category.CategoryObserveCategoryUseCase
import com.haghpanah.pienote.usecase.category.CategoryUpdateCategoryUseCase
import com.haghpanah.pienote.usecase.category.CategoryUpdateImageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(
//    val snackbarManager: SnackbarManager,
    private val getCategoryUseCase: CategoryObserveCategoryUseCase,
    private val updateCategoryUseCase: CategoryUpdateCategoryUseCase,
    private val deleteNoteFromCategoryUseCase: CategoryDeleteNoteFromCategoryUseCase,
    private val updateImageUseCase: CategoryUpdateImageUseCase,
    private val observeAvailableNotesUseCase: CategoryObserveAvailableNotesUseCase,
    private val addNoteToCategoryUseCase: CategoryAddNoteToCategoryUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CategoryViewState>(
    initialState = CategoryViewState(
        id = (savedStateHandle.get<Long>("id")
            ?: error("null category id from saved state handler"))
    ),
) {
    init {
        getCategoryInfo()
        getAvailableNotes()
    }

    private fun getAvailableNotes() {
        val availableNotes = observeAvailableNotesUseCase()

        viewModelScope.launch {
            availableNotes.collect { notes ->
                updateState {
                    copy(availableNotesToAdd = notes)
                }
            }
        }
    }

    private fun getCategoryInfo() {
        val category = getCategoryUseCase(getCurrentState().id)

        viewModelScope.launch {
            category.collect { result ->
                updateState {
                    copy(
                        name = result.name,
                        priority = result.priority,
                        image = result.image,
                        notes = result.notes
                    )
                }
            }
        }
    }

    fun addNoteToCategory(noteId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            addNoteToCategoryUseCase(
                noteId = noteId,
                categoryId = getCurrentState().id
            )
        }
    }

    fun deleteNoteFromCategory(noteId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteNoteFromCategoryUseCase(noteId)

//            snackbarManager.sendWarning(
//                message = "Note Removed From Category"
//            )
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
                uri = uri?.path
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
}