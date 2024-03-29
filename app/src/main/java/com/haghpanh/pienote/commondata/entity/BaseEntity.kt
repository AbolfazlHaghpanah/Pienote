package com.haghpanh.pienote.commondata.entity

interface BaseEntity<DM> {
    fun toDomainModel(): DM
}