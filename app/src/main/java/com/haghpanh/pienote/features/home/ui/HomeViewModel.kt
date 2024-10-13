package com.haghpanh.pienote.features.home.ui

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.commonui.BaseViewModel
import com.haghpanh.pienote.commonui.utils.SnackbarManager
import com.haghpanh.pienote.commonui.utils.chunkedEven
import com.haghpanh.pienote.features.home.domain.model.CategoryWithNotesCountDomainModel
import com.haghpanh.pienote.features.home.domain.usecase.HomeAddNotesToCategoryUseCase
import com.haghpanh.pienote.features.home.domain.usecase.HomeDeleteNoteUseCase
import com.haghpanh.pienote.features.home.domain.usecase.HomeInsertCategoryUseCase
import com.haghpanh.pienote.features.home.domain.usecase.HomeObserveCategoriesUseCase
import com.haghpanh.pienote.features.home.domain.usecase.HomeObserveNotesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.java.KoinJavaComponent.inject

class HomeViewModel(
    savedStateHandle: SavedStateHandle,
    val snackbarManager: SnackbarManager,
    private val homeObserveNotesUseCase: HomeObserveNotesUseCase,
    private val homeObserveCategoriesUseCase: HomeObserveCategoriesUseCase,
    private val homeDeleteNoteUseCase: HomeDeleteNoteUseCase,
    private val insertCategoryUseCase: HomeInsertCategoryUseCase,
    private val addNotesToCategoryUseCase: HomeAddNotesToCategoryUseCase,
) : BaseViewModel<HomeViewState>(
    initialState = HomeViewState(),
    savedStateHandle = savedStateHandle
) {
    init {
        observeCategories()
        observeNotes()
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            val mappedNote = note.toDomainModel()

            homeDeleteNoteUseCase(mappedNote)
        }
    }

    fun addNewCategory(
        noteIds: List<Int>,
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
                        }
                    }
            }.onFailure {
                snackbarManager.sendError("Fail To Create Category")
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
                val mappedNotes = notes.map { it.toUiModel() }

                updateState { copy(notes = mappedNotes) }
            }
        }
    }

    private fun observeCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            homeObserveCategoriesUseCase().collect { categories ->
                val mappedCategories = categories.map { category ->
                    category.toUiModel()
                }

                updateState { copy(categoriesChunked = mappedCategories.chunkedEven()) }
            }
        }
    }

    private fun CategoryWithNotesCountDomainModel.toUiModel(): Category =
        Category(
            id = id,
            name = name,
            priority = priority,
            image = image,
            noteCounts = noteCount
        )

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
