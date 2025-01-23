package com.haghpanah.pienote.feature.home

import androidx.lifecycle.viewModelScope
import com.eygraber.uri.Uri
import com.haghpanah.pienote.core.utlis.BaseViewModel
import com.haghpanah.pienote.core.utlis.SnackbarManager
import com.haghpanah.pienote.core.utlis.chunkedEven
import com.haghpanah.pienote.model.NoteDomainModel
import com.haghpanah.pienote.usecase.home.HomeAddNotesToCategoryUseCase
import com.haghpanah.pienote.usecase.home.HomeDeleteNoteUseCase
import com.haghpanah.pienote.usecase.home.HomeInsertCategoryUseCase
import com.haghpanah.pienote.usecase.home.HomeObserveCategoriesUseCase
import com.haghpanah.pienote.usecase.home.HomeObserveNotesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
    val snackbarManager: SnackbarManager,
    private val homeObserveNotesUseCase: HomeObserveNotesUseCase,
    private val homeObserveCategoriesUseCase: HomeObserveCategoriesUseCase,
    private val homeDeleteNoteUseCase: HomeDeleteNoteUseCase,
    private val insertCategoryUseCase: HomeInsertCategoryUseCase,
    private val addNotesToCategoryUseCase: HomeAddNotesToCategoryUseCase,
) : BaseViewModel<HomeViewState>(
    initialState = HomeViewState(),
) {
    init {
        observeCategories()
        observeNotes()
    }

    fun deleteNote(note: NoteDomainModel) {
        viewModelScope.launch(Dispatchers.IO) {
            homeDeleteNoteUseCase(note)
        }
    }

    fun addNewCategory(
        noteIds: List<Long>,
        name: String,
        image: Uri?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                insertCategoryUseCase(
                    name = name,
                    image = image
                )
            }.onSuccess {
                // TODO this is Dozdi Way
                delay(200)
                getCurrentState()
                    .categoriesChunked
                    ?.firstOrNull()
                    ?.firstOrNull()
                    ?.let { category ->
                        if (category.name == name) {
                            addNoteToCategory(
                                noteIds = noteIds,
                                categoryId = category.id
                            )

                            snackbarManager.sendSuccess(
                                message = "Category Created",
                                action = { updateState { copy(movedToCategoryId = category.id) } }
                            )
                        }
                    }
            }.onFailure {
                snackbarManager.sendError("Fail To Create Category")
            }
        }
    }

    fun addNoteToCategory(
        noteIds: List<Long>,
        categoryId: Long
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                addNotesToCategoryUseCase(
                    noteIds = noteIds,
                    categoryId = categoryId
                )
            }.onSuccess {
                updateState { copy(movedToCategoryId = categoryId) }
            }
        }
    }

    private fun observeNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            homeObserveNotesUseCase().collect { notes ->
                updateState { copy(notes = notes) }
            }
        }
    }

    private fun observeCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            homeObserveCategoriesUseCase().collect { categories ->
                updateState { copy(categoriesChunked = categories.chunkedEven()) }
            }
        }
    }
}