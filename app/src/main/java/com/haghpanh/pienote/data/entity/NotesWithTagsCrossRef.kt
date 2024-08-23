package com.haghpanh.pienote.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "notes_with_tags",
    primaryKeys = ["note_id", "tag_id"]
)
data class NotesWithTagsCrossRef(
    @ColumnInfo(name = "note_id")
    val noteId: Int,

    @ColumnInfo(name = "tag_id")
    val tagId: Int
)
