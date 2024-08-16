package com.haghpanh.pienote.commondata.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haghpanh.pienote.commondata.entity.CategoryEntity
import com.haghpanh.pienote.commondata.entity.NoteEntity

@Dao
interface CommonDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertNotes(note: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCategory(category: CategoryEntity)

    @Query(
        """
            UPDATE notes 
            SET category_id = :categoryId
            WHERE notes.id IN (:noteIds)
        """
    )
    suspend fun addNotesToCategory(noteIds: List<Int>, categoryId: Int)
}
