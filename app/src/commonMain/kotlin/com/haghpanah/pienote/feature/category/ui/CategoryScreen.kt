package com.haghpanah.pienote.feature.category.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun CategoryScreen(
    navController: NavController
) {
    Box(Modifier.fillMaxSize()){
        Text("Category")
    }
}
