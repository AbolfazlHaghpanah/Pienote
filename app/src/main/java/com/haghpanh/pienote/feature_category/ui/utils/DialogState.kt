package com.haghpanh.pienote.feature_category.ui.utils

sealed interface DialogState {
    data object MainDialog : DialogState
    data object ChangeName : DialogState
    data object AddNote : DialogState
    data object Dismiss : DialogState
}