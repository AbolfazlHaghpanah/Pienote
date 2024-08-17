package com.haghpanh.pienote.commonui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.haghpanh.pienote.features.category.ui.CategoryScreen
import com.haghpanh.pienote.features.home.ui.HomeScreen
import com.haghpanh.pienote.features.library.ui.LibraryScreen
import com.haghpanh.pienote.features.note.ui.NoteScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavController
) {
    composable(route = AppScreens.LibraryScreen.route) {
        LibraryScreen(navController = navController)
    }

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
