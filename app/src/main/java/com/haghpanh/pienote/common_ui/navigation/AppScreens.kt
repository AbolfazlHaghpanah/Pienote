package com.haghpanh.pienote.common_ui.navigation

sealed class AppScreens(val route: String) {
    data object HomeScreen : AppScreens("home-screen/{parent}") {
        fun createRoute(parent: String) = "home-screen/$parent"
    }

    data object NoteScreen : AppScreens("note-screen/{id}/{isExist}/{parent}") {
        fun createRoute(id: Int, isExist: Boolean, parent: String) =
            "note-screen/$id/$isExist/$parent"
    }

    data object CategoryScreen : AppScreens("category-screen/{id}/{parent}") {
        fun createRoute(id: Int, parent: String) = "category-screen/$id/$parent"
    }

    data object FavoriteScreen : AppScreens("favorite-screen/{parent}") {
        fun createRoute(parent: String) = "favorite-screen/$parent"
    }

    data object LibraryScreen : AppScreens("library-screen")
}