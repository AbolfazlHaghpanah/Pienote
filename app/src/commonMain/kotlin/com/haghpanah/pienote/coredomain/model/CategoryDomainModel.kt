package com.haghpanah.pienote.coredomain.model

data class CategoryDomainModel(
    val id: Int,
    val name: String,
    val priority: Int?,
    val image: String?
)
