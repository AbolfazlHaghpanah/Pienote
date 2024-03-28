package com.haghpanh.pienote.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.haghpanh.pienote.baseui.theme.PienoteTheme

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PienoteTheme {
        HomeScreen(
            state = HomeViewState(
                notes = listOf(
                    Note(
                        0,
                        "No Time To Die",
                        "For example, if ExampleScreen is a destination in a navigation graph, call hiltViewModel() to get an instance of ExampleViewModel scoped to the destination as shown in the code snippet below:",
                        addedTime = ""
                    ),
                    Note(
                        0,
                        "No Time To Die",
                        "For example, if ExampleScreen is a destination in a navigation graph, call hiltViewModel() to get an instance of ExampleViewModel scoped to the destination as shown in the code snippet below:",
                        addedTime = ""
                    ),
                    Note(
                        0,
                        "No Time To Die",
                        "For example, if ExampleScreen is a destination in a navigation graph, call hiltViewModel() to get an instance of ExampleViewModel scoped to the destination as shown in the code snippet below:",
                        addedTime = ""
                    ),
                    Note(
                        0,
                        "No Time To Die",
                        "For example, if ExampleScreen is a destination in a navigation graph, call hiltViewModel() to get an instance of ExampleViewModel scoped to the destination as shown in the code snippet below:",
                        addedTime = ""
                    ),
                    Note(
                        0,
                        "No Time To Die",
                        "For example, if ExampleScreen is a destination in a navigation graph, call hiltViewModel() to get an instance of ExampleViewModel scoped to the destination as shown in the code snippet below:",
                        addedTime = ""
                    ),

                    ),
                null,
                null
            ),
            {},
            {},
            {},
            {},
            {}
        )
    }
}