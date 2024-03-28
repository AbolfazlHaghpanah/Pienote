package com.haghpanh.pienote.home.data.repository

import com.haghpanh.pienote.commondata.entity.NoteEntity
import com.haghpanh.pienote.home.data.localdatasource.HomeLocalDataSource
import com.haghpanh.pienote.home.domain.model.NoteDomainModel
import com.haghpanh.pienote.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeLocalDataSource: HomeLocalDataSource
) : HomeRepository {
    override fun observeNotes(): Flow<List<NoteDomainModel>> {
        val notesFlow = homeLocalDataSource.observeNotes()

        return notesFlow.map { notes ->
            notes.map { note ->
                note.toDomainModel()
            }
        }
    }

    override suspend fun insertNote(note: NoteDomainModel) {
        val mappedNote = note.toNoteEntity()

        homeLocalDataSource.insertNote(mappedNote)
    }

    private fun NoteDomainModel.toNoteEntity(): NoteEntity =
        NoteEntity(
            id = id,
            title = title,
            note = note,
            image = image,
            addedTime = addedTime,
            lastChangedTime = lastChangedTime,
            categoryId = categoryId,
            priority = priority
        )

    private fun NoteEntity.toDomainModel(): NoteDomainModel =
        NoteDomainModel(
            id = id,
            title = title,
            note = note,
            image = image,
            addedTime = addedTime,
            lastChangedTime = lastChangedTime,
            categoryId = categoryId,
            priority = priority
        )
}