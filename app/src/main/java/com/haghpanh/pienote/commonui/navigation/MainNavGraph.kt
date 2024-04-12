package com.haghpanh.pienote.commonui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.haghpanh.pienote.home.ui.HomeScreen
import com.haghpanh.pienote.note.ui.NoteScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavController
) {
    composable(AppScreens.HomeScreen.route) {
        HomeScreen(navController = navController)
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
