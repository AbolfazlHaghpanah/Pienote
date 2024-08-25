package com.haghpanh.pienote.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String?,
    val color: String,
    @ColumnInfo(name = "category_id")
    val categoryId: Int
)
