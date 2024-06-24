package com.haghpanh.pienote.features.note.utils

sealed interface FocusRequestType {
    data object Title : FocusRequestType
    data object Note: FocusRequestType
    data object Non : FocusRequestType
}