package com.haghpanh.pienote.commondomain.repository

import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.commondomain.model.NoteDomainModel

interface CommonRepository {
    suspend fun insertNote(note: NoteDomainModel)
    suspend fun insertCategory(name: String, image: String?)
    suspend fun addNotesToCategory(noteIds: List<Int>, categoryId: Int)
}
