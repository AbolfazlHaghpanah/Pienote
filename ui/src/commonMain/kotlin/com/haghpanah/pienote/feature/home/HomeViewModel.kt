package com.haghpanah.pienote.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.haghpanah.pienote.core.utlis.BaseViewModel
import com.haghpanah.pienote.core.utlis.chunkedEven
import com.haghpanah.pienote.domain.model.NoteDomainModel
import com.haghpanah.pienote.domain.usecase.HomeAddNotesToCategoryUseCase
import com.haghpanah.pienote.domain.usecase.HomeDeleteNoteUseCase
import com.haghpanah.pienote.domain.usecase.HomeInsertCategoryUseCase
import com.haghpanah.pienote.domain.usecase.HomeObserveCategoriesUseCase
import com.haghpanah.pienote.domain.usecase.HomeObserveNotesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
//    val snackbarManager: SnackbarManager,
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
        noteIds: List<Int>,
        name: String,
        image: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                insertCategoryUseCase(
                    name = name,
                    image = ""
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
                        }
                    }
            }.onFailure {
                //TODO
//                snackbarManager.sendError("Fail To Create Category")
            }
        }
    }

    fun addNoteToCategory(
        noteIds: List<Int>,
        categoryId: Int
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