package pienote.commonui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import pienote.features.category.ui.CategoryScreen
import pienote.features.home.ui.HomeScreen
import pienote.features.note.ui.NoteScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavController
) {
    composable(AppScreens.HomeScreen.route) {
        HomeScreen(navController = navController)
    }

    composable(
        route = AppScreens.CategoryScreen.route,
        arguments = listOf(
            navArgument(name = "id") {
                type = NavType.IntType
                nullable = false
            }
        )
    ) {
        CategoryScreen(navController = navController)
    }

    composable(
        route = AppScreens.NoteScreen.route,
        arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
                nullable = false
            },
            navArgument("isExist") {
                type = NavType.BoolType
                nullable = false
            }
        )
    ) {
        NoteScreen(navController = navController)
    }
}
