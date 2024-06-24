package com.haghpanh.pienote.features.home.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haghpanh.pienote.commondata.entity.CategoryEntity
import com.haghpanh.pienote.commondata.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeDao {
    @Query(
        """
        select * from notes
        where category_id is null 
        order by id desc
    """
    )
    fun observeNotes(): Flow<List<NoteEntity>>

    @Query("select * from categories order by id desc")
    fun observeCategories(): Flow<List<CategoryEntity>>

    @Query("select * from notes where category_id =:categoryId order by id desc")
    fun observeNoteByCategory(categoryId: Int): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertNotes(note: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCategory(category: CategoryEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)
}