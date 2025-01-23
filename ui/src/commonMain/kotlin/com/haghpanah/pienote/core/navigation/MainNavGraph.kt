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
    composable<PienoteScreens.HomeScreen> {
        HomeScreen(navController = navHostController)
    }

    composable<PienoteScreens.CategoryScreen> {
        CategoryScreen(navController = navHostController)
    }

    composable<PienoteScreens.NoteScreen> {
        NoteScreen(navController = navHostController)
    }
}