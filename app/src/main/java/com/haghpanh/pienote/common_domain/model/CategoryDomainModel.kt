package com.haghpanh.pienote.common_domain.model

data class CategoryDomainModel(
    val id: Int,
    val name: String,
    val priority: Int?,
    val image: String?
)