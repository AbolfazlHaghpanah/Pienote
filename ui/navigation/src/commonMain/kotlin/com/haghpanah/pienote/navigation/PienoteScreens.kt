package com.haghpanah.pienote.navigation

import kotlinx.serialization.Serializable

sealed class PienoteScreens {

    @Serializable
    data object HomeScreen : PienoteScreens()

    @Serializable
    data class NoteScreen(
        val id: Long,
        val isExist: Boolean,
        val parent: String,
    ) : PienoteScreens()

    @Serializable
    data class CategoryScreen(
        val id: Long,
        val parent: String,
    ) : PienoteScreens()
}
