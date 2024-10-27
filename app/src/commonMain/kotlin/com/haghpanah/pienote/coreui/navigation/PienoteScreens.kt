package com.haghpanah.pienote.coreui.navigation

sealed class PienoteScreens(val route: String) {
    data object HomeScreen : PienoteScreens("home-screen")

    data object NoteScreen : PienoteScreens("note-screen/{id}/{isExist}/{parent}") {
        fun createRoute(id: Int, isExist: Boolean, parent: String) =
            "note-screen/$id/$isExist/$parent"
    }

    data object CategoryScreen : PienoteScreens("category-screen/{id}/{parent}") {
        fun createRoute(id: Int, parent: String) =
            "category-screen/$id/$parent"
    }
}
