package pienote.features.note.ui

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import pienote.commondomain.model.CategoryDomainModel
import pienote.commondomain.model.NoteDomainModel
import pienote.commonui.BaseViewModel
import pienote.features.note.domain.usecase.NoteGetCategoriesUseCase
import pienote.features.note.domain.usecase.NoteInsertNoteUseCase
import pienote.features.note.domain.usecase.NoteObserveNoteInfoUseCase
import pienote.features.note.domain.usecase.NoteUpdateNoteImageUseCase
import pienote.features.note.domain.usecase.NoteUpdateNoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(
    private val observeNoteInfoUseCase: NoteObserveNoteInfoUseCase,
    private val getCategoriesUseCase: NoteGetCategoriesUseCase,
    private val insertNoteUseCase: NoteInsertNoteUseCase,
    private val updateNoteUseCase: NoteUpdateNoteUseCase,
    private val noteUpdateNoteImageUseCase: NoteUpdateNoteImageUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<NoteViewState>(
    initialState = NoteViewState(
        isExist = savedStateHandle.get<Boolean>("isExist") ?: false,
        noteId = savedStateHandle.get<Int>("id") ?: -1
    ),
    savedStateHandle = savedStateHandle
) {
    init {
        getNoteInfo()
        getCategories()
    }

    override fun onCleared() {
        updateOrInsertNote()
        super.onCleared()
    }

    fun switchEditMode(
        currentNoteTitle: String,
        currentNoteMarkdown: String
    ) {
        updateState {
            copy(
                note = getCurrentState().note.copy(
                    note = currentNoteMarkdown,
                    title = currentNoteTitle
                )
            )
        }

        if (getCurrentState().isEmptyNote) return

        updateState {
            copy(isEditing = !isEditing)
        }
    }

    fun onNavigateBackRequest(
        currentNoteTitle: String,
        currentNoteMarkdown: String
    ) {
        updateState {
            copy(
                note = getCurrentState().note.copy(
                    note = currentNoteMarkdown,
                    title = currentNoteTitle
                )
            )
        }

        if (getCurrentState().isEditing && !getCurrentState().isEmptyNote) {
            switchEditMode(
                currentNoteTitle = currentNoteTitle,
                currentNoteMarkdown = currentNoteMarkdown
            )
        } else if (!getCurrentState().isEmptyNote) {
            updateOrInsertNote()
            updateState { copy(canNavigateBack = true) }
        } else {
            updateState { copy(canNavigateBack = true) }
        }
    }

    fun updateCategory(value: Int?) {
        updateState { copy(note = note.copy(categoryId = value)) }
        updateOrInsertNote()
    }

    private fun updateOrInsertNote() {
        if (getCurrentState().isExist) {
            updateCurrentNote()
        } else {
            insertCurrentNote()
        }
    }

    fun updateNoteImage(uri: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            val note = getCurrentState().note.toDomainModel()
            val newImage = noteUpdateNoteImageUseCase(note = note, uri = uri)
            val newNote = getCurrentState().note.copy(image = newImage?.toString())

            updateState { copy(note = newNote) }
        }
    }

    fun updateNoteText(value: String) {
        updateState {
            val newNote = note.copy(note = value)
            copy(note = newNote)
        }
    }

    fun updateNoteColor(value: String?) {
        updateState {
            val newNote = note.copy(color = value)
            copy(note = newNote)
        }
    }

    fun updateTitleText(value: String) {
        updateState {
            val newNote = note.copy(title = value)
            copy(note = newNote)
        }
    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val categories = getCategoriesUseCase().map { it.toUiModel() }
            updateState { copy(categories = categories) }
        }
    }

    private fun updateCurrentNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val note = getCurrentState().note.toDomainModel()
            updateNoteUseCase(note)
        }
    }

    private fun insertCurrentNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val note = getCurrentState().note.toDomainModel()
            val noteId = insertNoteUseCase(note)

            updateState { copy(isExist = true, noteId = noteId) }
            getNoteInfo()
        }
    }

    private fun getNoteInfo() {
        if (getCurrentState().isExist) {
            viewModelScope.launch(Dispatchers.IO) {
                if (getCurrentState().noteId == null) return@launch

                observeNoteInfoUseCase(getCurrentState().noteId!!).collect { noteWithCat ->
                    updateState {
                        copy(
                            note = noteWithCat.note.toUiModel(),
                            category = noteWithCat.category?.toUiModel()
                        )
                    }
                }
            }
        } else {
            updateState {
                copy(
                    isEditing = true,
                    note = Note()
                )
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
            color = color,
            categoryId = categoryId
        )

    private fun CategoryDomainModel.toUiModel(): Category =
        Category(
            id = id,
            name = name,
            priority = priority,
            image = image
        )
}
