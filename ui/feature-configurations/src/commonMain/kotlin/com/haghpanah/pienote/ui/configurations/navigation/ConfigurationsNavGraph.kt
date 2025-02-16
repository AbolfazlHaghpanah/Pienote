package com.haghpanah.pienote.ui.configurations.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.haghpanah.pienote.navigation.PienoteScreens
import com.haghpanah.pienote.ui.configurations.home.ConfigurationsHomeScreen

fun NavGraphBuilder.configurationsNavGraph(
    navController: NavController,
) {
    navigation<PienoteScreens.Configurations>(startDestination = ConfigurationsScreens.Home) {
        composable<ConfigurationsScreens.Home> {
            ConfigurationsHomeScreen(navController)
        }
    }
}