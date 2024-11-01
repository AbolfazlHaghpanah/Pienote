package com.haghpanah.pienote.feature.category.component

sealed interface DialogState {
    data object MainDialog : DialogState
    data object ChangeName : DialogState
    data object AddNote : DialogState
    data object Dismiss : DialogState
}