package com.haghpanh.pienote.features.note.data.repository

import com.haghpanh.pienote.commondata.utils.toEntity
import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.features.note.data.localdateasource.NoteLocalDataSource
import com.haghpanh.pienote.features.note.domain.model.NoteWithCategoryDomainModel
import com.haghpanh.pienote.features.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteLocalDataSource: NoteLocalDataSource
) : NoteRepository {
    override fun observeNote(id: Int): Flow<NoteWithCategoryDomainModel> =
        noteLocalDataSource.observeNote(id).map { it.toDomainModel() }

    override fun getCategories(): List<CategoryDomainModel> =
        noteLocalDataSource.getCategories().map { it.toDomainModel() }

    override suspend fun insertNote(note: NoteDomainModel) {
        val mappedNote = note.toEntity(true)

        noteLocalDataSource.insertNote(mappedNote)
    }

    override suspend fun updateNote(note: NoteDomainModel) {
        val mappedNote = note.toEntity()

        noteLocalDataSource.updateNote(mappedNote)
    }
}
