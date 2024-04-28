package com.haghpanh.pienote.feature_library.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.haghpanh.pienote.R
import com.haghpanh.pienote.common_ui.component.PienoteTopBar
import com.haghpanh.pienote.common_ui.navigation.AppScreens
import com.haghpanh.pienote.common_ui.theme.PienoteTheme

@Composable
fun LibraryScreen(
    navController: NavController
) {
    LibraryScreen {
        navController.navigate(it) {
            launchSingleTop = true
            popUpTo(it) {
                inclusive = true
            }
        }
    }
}

@Composable
fun LibraryScreen(
    navigateToRoute: (String) -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 14.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            PienoteTopBar(title = "Library")

            libsItems.forEach {
                Divider(Modifier.padding(horizontal = 24.dp))

                LibsItem(title = it.title, icon = it.iconId) {
                    navigateToRoute(it.route)
                }
            }
        }
    }
}


@Immutable
data class LibsItem(
    val title: String,
    val iconId: Int,
    val route: String
)

const val LIBRARY_SCREEN_NAME = "Library"

val libsItems = listOf(
    LibsItem(
        title = "Home",
        iconId = R.drawable.home,
        route = AppScreens.HomeScreen.createRoute(LIBRARY_SCREEN_NAME)
    ),
    LibsItem(
        title = "Categories",
        iconId = R.drawable.folder,
        route = AppScreens.HomeScreen.createRoute(LIBRARY_SCREEN_NAME)
    ),
    LibsItem(
        title = "Notes",
        iconId = R.drawable.description,
        route = AppScreens.NoteScreen.createRoute(
            id = -1,
            isExist = false,
            parent = LIBRARY_SCREEN_NAME
        )
    ),
    LibsItem(
        title = "Favorite",
        iconId = R.drawable.high_priority,
        route = AppScreens.FavoriteScreen.createRoute(LIBRARY_SCREEN_NAME)
    ),
    LibsItem(
        title = "Notes List",
        iconId = R.drawable.density_medium,
        route = AppScreens.NotesListScreen.createRoute(LIBRARY_SCREEN_NAME)
    )
    )

@Composable
fun LibsItem(
    title: String,
    icon: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(PienoteTheme.shapes.medium)
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(34.dp),
            painter = painterResource(id = icon),
            contentDescription = title
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = title,
            style = PienoteTheme.typography.h6
        )
    }
}