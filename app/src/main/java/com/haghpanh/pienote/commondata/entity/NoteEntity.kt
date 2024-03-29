package com.haghpanh.pienote.commondata.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.haghpanh.pienote.home.domain.model.NoteDomainModel

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "note")
    val note: String,
    @ColumnInfo(name = "image")
    val image: String?,
    @ColumnInfo(name = "added_time")
    val addedTime: String,
    @ColumnInfo(name = "last_changed_time")
    val lastChangedTime: String?,
    @ColumnInfo(name = "category_id")
    val categoryId: Int?,
    @ColumnInfo(name = "priority")
    val priority: Int?
) : BaseEntity<NoteDomainModel> {
    override fun toDomainModel(): NoteDomainModel =
        NoteDomainModel(
            id = id,
            title = title,
            note = note,
            image = image,
            addedTime = addedTime,
            lastChangedTime = lastChangedTime,
            categoryId = categoryId
        )
}
