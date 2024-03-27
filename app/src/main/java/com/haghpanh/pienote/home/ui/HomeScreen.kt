package com.haghpanh.pienote.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.haghpanh.pienote.baseui.theme.PienoteTheme

@Composable
fun HomeScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(
            modifier = Modifier
                .padding(24.dp),
            style = PienoteTheme.typography.h1,
            text = "This could be a Nice word",
            color = PienoteTheme.colors.onBackground
        )

        Text(
            modifier = Modifier
                .padding(24.dp),
            style = PienoteTheme.typography.body1,
            text = "These are the harmonies for Hex 474A51 color. Our color palette generator creates beautiful color palettes from any color. We use 9 different algorithms to find your perfect color harmonies.",
            color = PienoteTheme.colors.onBackground
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .height(54.dp),
            shape = PienoteTheme.shapes.large,
            onClick = { /*TODO*/ }) {
            Text(text = "Do Not Click")
        }
    }
}