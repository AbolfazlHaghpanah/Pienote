package com.haghpanah.pienote.feature.note.utils

sealed interface FocusRequestType {
    data object Title : FocusRequestType
    data object Note : FocusRequestType
    data object Non : FocusRequestType
}