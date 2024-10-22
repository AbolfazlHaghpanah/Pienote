package pienote.features.category.ui

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import pienote.commondomain.model.CategoryDomainModel
import pienote.commondomain.model.NoteDomainModel
import pienote.commonui.BaseViewModel
import pienote.commonui.utils.SnackbarManager
import pienote.features.category.domain.usecase.CategoryAddNoteToCategoryUseCase
import pienote.features.category.domain.usecase.CategoryDeleteNoteFromCategoryUseCase
import pienote.features.category.domain.usecase.CategoryObserveAvailableNotesUseCase
import pienote.features.category.domain.usecase.CategoryObserveCategoryUseCase
import pienote.features.category.domain.usecase.CategoryUpdateCategoryUseCase
import pienote.features.category.domain.usecase.CategoryUpdateImageUseCase
import pienote.features.note.ui.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(
    val snackbarManager: SnackbarManager,
    private val getCategoryUseCase: CategoryObserveCategoryUseCase,
    private val updateCategoryUseCase: CategoryUpdateCategoryUseCase,
    private val deleteNoteFromCategoryUseCase: CategoryDeleteNoteFromCategoryUseCase,
    private val updateImageUseCase: CategoryUpdateImageUseCase,
    private val observeAvailableNotesUseCase: CategoryObserveAvailableNotesUseCase,
    private val addNoteToCategoryUseCase: CategoryAddNoteToCategoryUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CategoryViewState>(
    initialState = CategoryViewState(
        id = savedStateHandle.get<Int>("id") ?: -1
    ),
    savedStateHandle = savedStateHandle
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
                    copy(availableNotesToAdd = notes.map { note -> note.toUiModel() })
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
                        notes = result.notes.map { it.toUiModel() }
                    )
                }
            }
        }
    }

    fun addNoteToCategory(noteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            addNoteToCategoryUseCase(
                noteId = noteId,
                categoryId = getCurrentState().id
            )
        }
    }

    fun deleteNoteFromCategory(noteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteNoteFromCategoryUseCase(noteId)

            snackbarManager.sendWarning(
                message = "Note Removed From Category"
            )
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
            color = color
        )
}
