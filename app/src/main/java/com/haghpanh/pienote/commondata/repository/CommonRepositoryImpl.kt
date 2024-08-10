package com.haghpanh.pienote.commondata.repository

import com.haghpanh.pienote.commondata.dao.CommonDao
import com.haghpanh.pienote.commondata.entity.CategoryEntity
import com.haghpanh.pienote.commondata.utils.toEntity
import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.commondomain.repository.CommonRepository
import javax.inject.Inject

class CommonRepositoryImpl @Inject constructor(
    private val commonDao: CommonDao
) : CommonRepository {
    override suspend fun insertNote(note: NoteDomainModel) {
        commonDao.insertNotes(note.toEntity(true))
    }

    override suspend fun insertCategory(name: String, image: String?) {
        commonDao.insertCategory(
            CategoryEntity(
                name = name,
                priority = null,
                image = image
            )
        )
    }

    override suspend fun addNotesToCategory(noteIds: List<Int>, categoryId: Int) {
        commonDao.addNotesToCategory(
            noteIds = noteIds,
            categoryId = categoryId
        )
    }
}
