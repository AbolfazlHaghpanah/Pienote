package com.haghpanh.pienote.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.haghpanh.pienote.baseui.theme.PienoteTheme

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PienoteTheme {
        HomeScreen(navController = rememberNavController())
    }
}