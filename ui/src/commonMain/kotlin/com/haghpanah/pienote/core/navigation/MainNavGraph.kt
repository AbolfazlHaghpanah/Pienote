package com.haghpanah.pienote.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.haghpanah.pienote.feature.category.CategoryScreen
import com.haghpanah.pienote.feature.home.HomeScreen
import com.haghpanah.pienote.feature.note.NoteScreen

fun NavGraphBuilder.mainNavGraph(navHostController: NavHostController) {
    composable(PienoteScreens.HomeScreen.route) {
        HomeScreen(navController = navHostController)
    }

    composable(
        route = PienoteScreens.CategoryScreen.route,
        arguments = listOf(
            navArgument(name = "id") {
                type = NavType.IntType
                nullable = false
            }
        )
    ) {
        CategoryScreen(navController = navHostController)
    }

    composable(
        route = PienoteScreens.NoteScreen.route,
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
        NoteScreen(navHostController)
    }
}