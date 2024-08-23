package com.haghpanh.pienote.data.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.haghpanh.pienote.data.entity.NoteEntity
import com.haghpanh.pienote.data.entity.NotesWithTagsCrossRef
import com.haghpanh.pienote.data.entity.TagEntity

data class NoteWithTags(
    @Embedded
    val note: NoteEntity,
    @Relation(
        entityColumn = "tag_id",
        parentColumn = "id",
        associateBy = Junction(
            NotesWithTagsCrossRef::class,
            parentColumn = "note_id",
            entityColumn = "tag_id"
        )
    )
    val tags: List<TagEntity>
)
