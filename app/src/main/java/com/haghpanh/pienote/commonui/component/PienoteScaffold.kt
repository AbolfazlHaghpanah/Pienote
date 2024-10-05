package com.haghpanh.pienote.commonui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    content: @Composable BoxScope.(PaddingValues) -> Unit
) {
    var paddingValues by remember { mutableStateOf(PaddingValues(0.dp)) }
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
        content(paddingValues)

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
                        paddingValues = PaddingValues(
                            top = paddingValues.calculateTopPadding(),
                            bottom = it.size.height.toDp()
                        )
                    }
                }
                .align(Alignment.BottomCenter)
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
                .onGloballyPositioned {
                    with(density) {
                        paddingValues = PaddingValues(
                            top = it.size.height.toDp(),
                            bottom = paddingValues.calculateBottomPadding()
                        )
                    }
                }
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        ) {
            topBar()
        }
    }
}
