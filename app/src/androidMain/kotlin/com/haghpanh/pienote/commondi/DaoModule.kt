package pienote.commondi

import pienote.data.dao.CategoryDao
import pienote.data.dao.NoteDao
import pienote.data.utils.PienoteDatabase
import org.koin.dsl.module

val daoModule = module {
    single<NoteDao> { get<PienoteDatabase>().NoteDao() }
    single<CategoryDao> { get<PienoteDatabase>().CategoryDao() }
}
