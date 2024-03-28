package com.haghpanh.pienote.home.domain.repository

import com.haghpanh.pienote.home.domain.model.NoteDomainModel
import com.haghpanh.pienote.home.domain.model.QuickNoteDomainModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun observeNotes () : Flow<List<NoteDomainModel>>
    suspend fun insertNote (note : QuickNoteDomainModel)
}