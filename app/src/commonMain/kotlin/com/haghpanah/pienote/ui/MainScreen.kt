package com.haghpanah.pienote.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.haghpanah.pienote.core.navigation.PienoteScreens
import com.haghpanah.pienote.core.navigation.mainNavGraph
import com.haghpanah.pienote.core.theme.PienoteTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun MainScreen() {
    PienoteTheme {
        val navController = rememberNavController()

        Box(
            modifier = Modifier
                .background(PienoteTheme.colors.background)
                .navigationBarsPadding()
                .fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = PienoteScreens.HomeScreen.route
            ) {
                mainNavGraph(navController)
            }
        }
    }
}