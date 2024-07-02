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

data class SpcfFavorite(
    val favoriteType: String?
)

data class SpcfNotes(
    val addedTime: String?
)

data class GenrNotes(
    val id: Int,
    val note: String,
    val title: String,
)

data class NotesResult(
    @Embedded
    val genrNotes: GenrNotes,
    @Embedded
    val spcfNotes: SpcfNotes?,
    @Embedded
    val spcfFavorite: SpcfFavorite?
) {
    fun toDomainModel() =
        NoteDomainModel(
            id = genrNotes.id,
            title = genrNotes.title,
            note = genrNotes.note,
            addedTime = spcfNotes?.addedTime ?: spcfFavorite?.favoriteType ?: "null"
        )
}
