package com.haghpanh.pienote.features.favorite.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.haghpanh.pienote.commondata.entity.BaseEntity
import com.haghpanh.pienote.commondomain.model.NoteDomainModel

@Entity(tableName = "favorite_notes")
data class FavoriteNotesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val note: String,
    val image: String?,
    @ColumnInfo(name = "last_changed_time")
    val lastChangedTime: String?,
    @ColumnInfo(name = "category_id")
    val categoryId: Int?,
    @ColumnInfo(name = "favorite_type")
    val favoriteType : String
) : BaseEntity<NoteDomainModel> {
    override fun toDomainModel(): NoteDomainModel =
        NoteDomainModel(
            id = id,
            title = title,
            note = note,
            image = image,
            addedTime = "addedTime",
            lastChangedTime = lastChangedTime,
            categoryId = categoryId
        )
}
