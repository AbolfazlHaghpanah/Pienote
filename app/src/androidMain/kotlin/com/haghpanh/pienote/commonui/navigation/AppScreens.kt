package pienote.commonui.navigation

sealed class AppScreens(val route: String) {
    data object HomeScreen : AppScreens("home-screen")

    data object NoteScreen : AppScreens("note-screen/{id}/{isExist}/{parent}") {
        fun createRoute(id: Int, isExist: Boolean, parent: String) =
            "note-screen/$id/$isExist/$parent"
    }

    data object CategoryScreen : AppScreens("category-screen/{id}/{parent}") {
        fun createRoute(id: Int, parent: String) =
            "category-screen/$id/$parent"
    }
}
