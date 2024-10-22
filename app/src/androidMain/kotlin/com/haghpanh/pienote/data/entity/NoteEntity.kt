package pienote.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pienote.commondomain.model.NoteDomainModel

@Entity(
    tableName = "notes"
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val note: String,
    val image: String?,
    @ColumnInfo(name = "added_time")
    val addedTime: String,
    @ColumnInfo(name = "last_changed_time")
    val lastChangedTime: String?,
    @ColumnInfo(name = "category_id")
    val categoryId: Int?,
    @ColumnInfo(name = "color")
    val color: String?
) {
    fun toDomainModel(): NoteDomainModel =
        NoteDomainModel(
            id = id,
            title = title,
            note = note,
            image = image,
            addedTime = addedTime,
            lastChangedTime = lastChangedTime,
            categoryId = categoryId,
            color = color
        )
}
