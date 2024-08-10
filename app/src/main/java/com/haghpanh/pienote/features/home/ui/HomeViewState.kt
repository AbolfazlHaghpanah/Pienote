package com.haghpanh.pienote.features.home.ui

import androidx.compose.runtime.Immutable
import com.haghpanh.pienote.commonui.utils.Result

@Immutable
data class HomeViewState(
    val notes: List<Note>? = null,
    val categories: List<Category>? = null,
    val addToCategoryResult: Result<Int> = Result.Idle(),
)
