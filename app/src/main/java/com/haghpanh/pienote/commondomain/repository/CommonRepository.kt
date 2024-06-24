package com.haghpanh.pienote.commondomain.repository

import com.haghpanh.pienote.commondomain.model.NoteDomainModel

interface CommonRepository {
    suspend fun insertNote(note: NoteDomainModel)
}