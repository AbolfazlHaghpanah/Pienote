package com.haghpanah.pienote.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.haghpanah.pienote.designsystem.theme.PienoteTheme
import com.haghpanah.pienote.navigation.PienoteScreens
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun MainScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(PienoteTheme.colors.background)
            .navigationBarsPadding()
            .fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = PienoteScreens.HomeScreen
        ) {
            mainNavGraph(navController)
        }
    }
}