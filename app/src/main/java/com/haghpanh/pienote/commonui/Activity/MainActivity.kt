package com.haghpanh.pienote.commonui.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.haghpanh.pienote.commonui.navigation.AppScreens
import com.haghpanh.pienote.commonui.navigation.mainNavGraph
import com.haghpanh.pienote.commonui.theme.PienoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PienoteTheme {
                val navController = rememberNavController()

                MainScreen(navController)

                SideEffect {
                    enableEdgeToEdge()
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .background(PienoteTheme.colors.background)
            .navigationBarsPadding()
            .fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = AppScreens.LibraryScreen.route
        ) {
            mainNavGraph(navController)
        }
    }
}
