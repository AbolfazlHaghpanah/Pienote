package com.haghpanh.pienote.feature_noteslist.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.haghpanh.pienote.common_data.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesListDao {
    @Query(
        """
            SELECT * FROM notes 
            UNION 
            SELECT * FROM favorite_notes
        """
    )
    fun observeNotes(): Flow<List<NoteEntity>>
}