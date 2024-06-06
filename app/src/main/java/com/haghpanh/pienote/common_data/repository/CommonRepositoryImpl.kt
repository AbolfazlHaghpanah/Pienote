package com.haghpanh.pienote.common_data.repository

import com.haghpanh.pienote.common_data.dao.CommonDao
import com.haghpanh.pienote.common_data.utils.toEntity
import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.common_domain.repository.CommonRepository
import javax.inject.Inject

class CommonRepositoryImpl @Inject constructor(
    private val commonDao: CommonDao
) : CommonRepository {
    override suspend fun insertNote(note: NoteDomainModel) {
        commonDao.insertNotes(note.toEntity(true))
    }
}