package com.haghpanah.pienote.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.mainNavGraph(navHostController: NavHostController){
    composable(PienoteScreens.HomeScreen.route) {
//        HomeScreen(navController = navHostController)
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
//        CategoryScreen(navController = navHostController)
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

    }
}