package com.haghpanh.pienote.baseui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.haghpanh.pienote.note.ui.NoteScreen
import com.haghpanh.pienote.home.ui.HomeScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavController
) {
    composable(AppScreens.HomeScreen.route) {
        HomeScreen(navController = navController)
    }

    composable(AppScreens.NoteScreen.route) {
        NoteScreen(navController = navController)
    }
}
