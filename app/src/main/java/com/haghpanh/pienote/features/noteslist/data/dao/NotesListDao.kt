package com.haghpanh.pienote.features.noteslist.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Query
import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesListDao {
    @Query(
        """
            select id,title,note,added_time, NULL as favorite_type  from  notes
            union all 
            select id,title,note,NULL as added_time,favorite_type from favorite_notes
        """
    )
    fun observeNotes(): Flow<List<NotesResult>>

    @Query(
        """
            select id,title,note,added_time, NULL as favorite_type  from  notes
            union all 
            select id,title,note,NULL as added_time,favorite_type from favorite_notes
        """
    )
    fun getPagedNotes(): PagingSource<Int, NotesResult>

}

data class spcfFavorite(
    val favorite_type: String?
)


data class spcfNotes(
    val addedTime: String?
)

data class genrNotes(
    val id: Int,
    val note: String,
    val title: String,
)

data class NotesResult(
    @Embedded
    val genrNotes: genrNotes,
    @Embedded
    val spcfNotes: spcfNotes?,
    @Embedded
    val spcfFavorite: spcfFavorite?
) {
    fun toDomainModel() =
        NoteDomainModel(
            id = genrNotes.id,
            title = genrNotes.title,
            note = genrNotes.note,
            addedTime = spcfNotes?.addedTime ?: spcfFavorite?.favorite_type ?: "null"
        )
}