package com.haghpanah.pienote.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.haghpanah.pienote.category.CategoryScreen
import com.haghpanah.pienote.home.HomeScreen
import com.haghpanah.pienote.navigation.PienoteScreens
import com.haghpanah.pienote.note.NoteScreen
import com.haghpanah.pienote.ui.configurations.ConfigurationsScreen

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

    composable<PienoteScreens.ConfigurationsScreen> {
        ConfigurationsScreen(navController = navHostController)
    }
}