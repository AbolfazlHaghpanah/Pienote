package com.haghpanh.pienote.commondi

import com.haghpanh.pienote.data.dao.CategoryDao
import com.haghpanh.pienote.data.dao.NoteDao
import com.haghpanh.pienote.data.utils.PienoteDatabase
import org.koin.dsl.module

val daoModule = module {
    single<NoteDao> { get<PienoteDatabase>().NoteDao() }
    single<CategoryDao> { get<PienoteDatabase>().CategoryDao() }
}
