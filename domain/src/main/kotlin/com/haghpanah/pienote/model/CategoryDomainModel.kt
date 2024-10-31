package com.haghpanah.pienote.model

data class CategoryDomainModel(
    val id: Long,
    val name: String,
    val priority: Int?,
    val image: String?
)
