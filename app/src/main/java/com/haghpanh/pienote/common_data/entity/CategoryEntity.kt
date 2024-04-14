package com.haghpanh.pienote.common_data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.haghpanh.pienote.common_domain.model.CategoryDomainModel

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val priority: Int?,
    val image: String?
) : BaseEntity<CategoryDomainModel> {
    override fun toDomainModel(): CategoryDomainModel =
        CategoryDomainModel(
            id = id,
            name = name,
            priority = priority,
            image = image
        )

}
