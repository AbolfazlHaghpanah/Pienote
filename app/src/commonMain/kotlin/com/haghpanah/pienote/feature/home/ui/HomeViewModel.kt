package com.haghpanah.pienote.feature.home.ui

import com.haghpanah.pienote.coreui.model.NoteUiModel
import com.haghpanah.pienote.coreui.utlis.BaseViewModel

class HomeViewModel() : BaseViewModel<HomeViewState>(
    HomeViewState(
        notes = listOf(
            NoteUiModel(
                0,
                "No Time To Die",
                "For example, if ExampleScreen is a destination in a navigation graph, call hiltViewModel() to get an instance of ExampleViewModel scoped to the destination as shown in the code snippet below:",
                addedTime = ""
            ),
            NoteUiModel(
                1,
                "No Time To Die",
                "For example, if ExampleScreen is a destination in a navigation graph, call hiltViewModel() to get an instance of ExampleViewModel scoped to the destination as shown in the code snippet below:",
                addedTime = ""
            ),
            NoteUiModel(
                2,
                "No Time To Die",
                "For example, if ExampleScreen is a destination in a navigation graph, call hiltViewModel() to get an instance of ExampleViewModel scoped to the destination as shown in the code snippet below:",
                addedTime = ""
            ),
            NoteUiModel(2,
                "No Time To Die",
                "For example, if ExampleScreen is a destination in a navigation graph, call hiltViewModel() to get an instance of ExampleViewModel scoped to the destination as shown in the code snippet below:",
                addedTime = ""
            ),
            NoteUiModel(
                3,
                "No Time To Die",
                "For example, if ExampleScreen is a destination in a navigation graph, call hiltViewModel() to get an instance of ExampleViewModel scoped to the destination as shown in the code snippet below:",
                addedTime = ""
            )
        )
    )
) {
}