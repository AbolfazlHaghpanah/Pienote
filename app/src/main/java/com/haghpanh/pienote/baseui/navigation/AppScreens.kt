package com.haghpanh.pienote.baseui.navigation

sealed class AppScreens(val route: String) {
    data object HomeScreen : AppScreens("home-screen")
    data object NoteScreen : AppScreens("note-screen/{id}/{isExist}") {
        fun createRoute(id: Int, isExist: Boolean): String = "note-screen/$id/$isExist"
    }

    data object CategoryScreen : AppScreens("category-screen")
}