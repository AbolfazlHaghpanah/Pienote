package com.haghpanh.pienote.feature_category.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.haghpanh.pienote.R
import com.haghpanh.pienote.common_ui.component.PienoteChip
import com.haghpanh.pienote.common_ui.component.PienoteDialog
import com.haghpanh.pienote.common_ui.navigation.AppScreens
import com.haghpanh.pienote.common_ui.theme.PienoteTheme
import com.haghpanh.pienote.feature_category.ui.components.CategoryDialogItem
import com.haghpanh.pienote.feature_category.ui.utils.CATEGORY_DIALOG_ITEM_EDIT_NAME_ID
import com.haghpanh.pienote.feature_category.ui.utils.categoryDialogItems
import com.haghpanh.pienote.feature_home.ui.component.HomeNoteItem

@Composable
fun CategoryScreen(
    navController: NavController
) {
    CategoryScreen(
        navController = navController,
        viewModel = hiltViewModel()
    )
}

@Composable
fun CategoryScreen(
    navController: NavController,
    viewModel: CategoryViewModel
) {
    val state by viewModel.collectAsStateWithLifecycle()
    val parentScreen = viewModel.savedStateHandler<String>("parent")

    CategoryScreen(
        state = state,
        parentScreen = parentScreen,
        navigateToRoute = { route -> navController.navigate(route) },
        onBack = { navController.popBackStack() }
    )
}

@Composable
fun CategoryScreen(
    state: CategoryViewState,
    parentScreen: String?,
    navigateToRoute: (String) -> Unit,
    onBack: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var shouldShowChangeName by remember { mutableStateOf(false) }
    var categoryName by remember { mutableStateOf(state.name) }

    val dialogItemsAction: (Int) -> Unit = {
        when (it) {
            CATEGORY_DIALOG_ITEM_EDIT_NAME_ID -> {
                shouldShowChangeName = true
            }
        }
    }

    Scaffold { paddingValues ->

        if (shouldShowChangeName) {
            PienoteDialog(onDismissRequest = { shouldShowChangeName = false }) {
                Column {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        value = categoryName,
                        onValueChange = { categoryName = it },
                        label = { Text(text = "Category Name") }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Discard")
                    }
                }
            }
        }

        if (showDialog) {
            PienoteDialog(
                titleSection = {
                    Column {
                        Text(
                            modifier = Modifier
                                .padding(start = 14.dp)
                                .fillMaxWidth(),
                            text = state.name,
                            style = PienoteTheme.typography.h6,
                            color = PienoteTheme.colors.onSurface
                        )

                        Text(
                            modifier = Modifier
                                .padding(start = 14.dp, top = 8.dp)
                                .fillMaxWidth(),
                            text = stringResource(R.string.notes, state.notes.size),
                            style = PienoteTheme.typography.subtitle2,
                            color = PienoteTheme.colors.onSurface
                        )
                    }
                },
                image = state.notes.firstOrNull()?.image?.toUri(),
                content = {
                    categoryDialogItems.forEach {
                        CategoryDialogItem(
                            title = it.title,
                            icon = it.icon
                        ) {
                            dialogItemsAction(it.id)
                        }
                    }
                },
                onDismissRequest = { showDialog = false }
            )
        }

        LazyColumn(
            modifier = Modifier
                .statusBarsPadding()
                .padding(paddingValues)
        ) {
            item {
                if (parentScreen != null) {
                    AnimatedVisibility(visible = !showDialog) {
                        PienoteChip(
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                            onClick = onBack
                        ) {
                            Row(
                                modifier = Modifier.padding(6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                    contentDescription = "back"
                                )

                                Text(
                                    modifier = Modifier.padding(end = 4.dp),
                                    text = parentScreen,
                                    style = PienoteTheme.typography.subtitle1
                                )
                            }
                        }
                    }
                }
            }

            item {
                AnimatedContent(
                    targetState = showDialog,
                    label = "on showing dialog screen"
                ) {
                    if (it) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp)
                        )
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier.padding(32.dp),
                                text = state.name,
                                style = PienoteTheme.typography.h1
                            )

                            PienoteChip(
                                modifier = Modifier
                                    .padding(horizontal = 24.dp)
                                    .size(42.dp)
                                    .aspectRatio(1f),
                                shape = PienoteTheme.shapes.rounded,
                                onClick = {
                                    showDialog = true
                                },
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .padding(6.dp)
                                        .fillMaxSize(),
                                    imageVector = Icons.Rounded.MoreVert,
                                    contentDescription = null,
                                    tint = PienoteTheme.colors.onBackground
                                )
                            }
                        }

                    }
                }
            }

            if (state.notes.isNotEmpty()) {
                items(state.notes) {
                    HomeNoteItem(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 14.dp),
                        title = it.title.orEmpty(),
                        note = it.note.orEmpty(),
                        onClick = {
                            navigateToRoute(
                                AppScreens.NoteScreen.createRoute(
                                    id = it.id,
                                    isExist = true,
                                    parent = state.name
                                )
                            )
                        },
                        onDelete = {}
                    )
                }
            } else {
                item {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "There is No Note in This Category Yet"
                        )
                    }
                }
            }
        }
    }
}
