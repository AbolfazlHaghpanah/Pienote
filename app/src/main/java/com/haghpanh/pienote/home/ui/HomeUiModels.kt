package com.haghpanh.pienote.home.ui

import androidx.compose.runtime.Immutable

@Immutable
data class Note(
    val id: Int,
    val title: String,
    val note: String,
    val image: String? = null,
    val addedTime: String,
    val lastChangedTime: String? = null,
    val categoryId: Int? = null,
    val priority: Int? = null
)

@Immutable
data class Category(
    val id: Int = 0,
    val name: String,
    val priority: Int?,
    val image: String?
)