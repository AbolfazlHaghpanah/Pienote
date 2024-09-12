package com.haghpanh.pienote.commonui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun PienoteScaffold(
    modifier: Modifier = Modifier,
    includeSystemBarsPadding: Boolean = true,
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    bottomMenu: @Composable () -> Unit = {},
    floatingActionButtonAlignment: Alignment = Alignment.BottomEnd,
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val paddingValues = remember {
        mutableStateOf(PaddingValues(0.dp))
    }
    val density = LocalDensity.current

    Box(
        modifier = modifier
            .then(
                if (includeSystemBarsPadding) {
                    Modifier.systemBarsPadding()
                } else {
                    Modifier
                }
            )
            .fillMaxSize()
    ) {
        content(paddingValues.value)

        Box(
            modifier = Modifier
                .padding(16.dp)
                .align(floatingActionButtonAlignment)
        ) {
            floatingActionButton()
        }

        Box(
            modifier = Modifier
                .onGloballyPositioned {
                    with(density) {
                        paddingValues.value = PaddingValues(bottom = it.size.height.toDp())
                    }
                }
                .align(Alignment.BottomCenter)
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            bottomMenu()
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            snackbarHost()
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .onGloballyPositioned {
                    with(density) {
                        paddingValues.value = PaddingValues(top = it.size.height.toDp())
                    }
                }
        ) {
            topBar()
        }
    }
}
