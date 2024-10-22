package pienote.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import pienote.data.entity.NoteEntity
import pienote.data.relation.NoteWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM NOTES")
    fun observeNotes(): Flow<List<NoteEntity>>

    @Transaction
    @Query("SELECT * FROM notes WHERE id =:id")
    fun observeNoteWithCategoryById(id: Int): Flow<NoteWithCategory>

    @Query(
        """
        SELECT * FROM notes
        WHERE category_id =:categoryId
        ORDER BY id DESC
    """
    )
    fun observeNoteByCategoryId(categoryId: Int): Flow<List<NoteEntity>>

    @Query(
        """
        SELECT * FROM notes
        WHERE category_id is null 
        ORDER BY id DESC
    """
    )
    fun observeNoCategoryNotes(): Flow<List<NoteEntity>>

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertNote(note: NoteEntity): Long

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Query(
        """
            UPDATE notes 
            SET category_id = :categoryId
            WHERE notes.id IN (:noteIds)
        """
    )
    suspend fun changeNotesCategory(noteIds: List<Int>, categoryId: Int)

    @Query(
        """
        UPDATE notes
        SET category_id = null 
        WHERE id = :noteId
        """
    )
    suspend fun deleteNoteFromCategory(noteId: Int)

    @Query(
        """
        UPDATE notes
        SET category_id = :categoryId
        WHERE id = :noteId
        """
    )
    suspend fun addNoteToCategory(noteId: Int, categoryId: Int)
}
