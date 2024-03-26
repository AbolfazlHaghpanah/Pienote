package com.haghpanh.pienote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.Text
import com.haghpanh.pienote.baseui.theme.PienoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PienoteTheme {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Do Not Click")
                }
            }
        }
    }
}
