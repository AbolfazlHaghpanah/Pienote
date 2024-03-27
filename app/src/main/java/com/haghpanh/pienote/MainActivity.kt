package com.haghpanh.pienote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.baseui.theme.PienoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PienoteTheme {
                SideEffect {
                    enableEdgeToEdge()
                }

                Box(
                    modifier = Modifier
                        .background(PienoteTheme.colors.background)
                        .statusBarsPadding()
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(24.dp),
                        style = PienoteTheme.typography.h1,
                        text = "This could be a Nice word",
                        color = PienoteTheme.colors.onBackground
                    )

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
        }
    }
}
