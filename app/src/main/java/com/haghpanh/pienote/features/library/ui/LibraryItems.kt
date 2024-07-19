package com.haghpanh.pienote.features.library.ui

import androidx.compose.runtime.Immutable
import com.haghpanh.pienote.R
import com.haghpanh.pienote.commonui.navigation.AppScreens

@Immutable
data class LibsItem(
    val title: String,
    val iconId: Int,
    val route: String
)

val libsItems = listOf(
    LibsItem(
        title = "Home",
        iconId = R.drawable.home,
        route = AppScreens.HomeScreen.createRoute(LIBRARY_SCREEN_NAME)
    ),
    LibsItem(
        title = "Categories",
        iconId = R.drawable.folder,
        route = AppScreens.HomeScreen.createRoute(LIBRARY_SCREEN_NAME)
    ),
    LibsItem(
        title = "Notes",
        iconId = R.drawable.description,
        route = AppScreens.NoteScreen.createRoute(
            id = -1,
            isExist = false,
            parent = LIBRARY_SCREEN_NAME
        )
    ),
    LibsItem(
        title = "Favorite",
        iconId = R.drawable.high_priority,
        route = AppScreens.FavoriteScreen.createRoute(LIBRARY_SCREEN_NAME)
    ),
    LibsItem(
        title = "Notes List",
        iconId = R.drawable.density_medium,
        route = AppScreens.NotesListScreen.createRoute(LIBRARY_SCREEN_NAME)
    )
)
