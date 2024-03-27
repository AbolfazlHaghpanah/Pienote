package com.haghpanh.pienote.baseui.navigation

sealed class AppScreens (val route : String){
    data object HomeScreen : AppScreens("home-screen")
    data object AddNoteScreen : AppScreens("add-note-screen")
}