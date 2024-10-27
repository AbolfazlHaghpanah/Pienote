package com.haghpanah.pienote.coreui.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.haghpanah.pienote.coreui.navigation.PienoteScreens
import com.haghpanah.pienote.coreui.navigation.mainNavGraph
import com.haghpanah.pienote.coreui.theme.PienoteTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun MainScreen() {
    PienoteTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = PienoteScreens.HomeScreen.route
        ) {
            mainNavGraph(navController)
        }
    }
}