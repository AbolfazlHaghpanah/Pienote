package com.haghpanh.pienote.feature_category.domain.model

import com.haghpanh.pienote.common_domain.model.NoteDomainModel

data class CategoryDomainModel(
    val id : Int,
    val name : String,
    val priority : Int?,
    val image : String?,
    val notes : List<NoteDomainModel>
)
