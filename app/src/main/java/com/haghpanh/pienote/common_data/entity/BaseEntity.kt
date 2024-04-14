package com.haghpanh.pienote.common_data.entity

interface BaseEntity<DM> {
    fun toDomainModel(): DM
}