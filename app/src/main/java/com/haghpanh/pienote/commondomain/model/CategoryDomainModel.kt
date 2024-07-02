package com.haghpanh.pienote.commondomain.model

data class CategoryDomainModel(
    val id: Int,
    val name: String,
    val priority: Int?,
    val image: String?
)
