package pienote.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import pienote.commondomain.model.CategoryDomainModel

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val priority: Int?,
    val image: String?
) {
    fun toDomainModel(): CategoryDomainModel =
        CategoryDomainModel(
            id = id,
            name = name,
            priority = priority,
            image = image
        )
}
