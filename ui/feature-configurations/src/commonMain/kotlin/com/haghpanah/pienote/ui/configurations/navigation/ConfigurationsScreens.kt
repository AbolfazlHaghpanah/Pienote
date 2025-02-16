package com.haghpanah.pienote.ui.configurations.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class ConfigurationsScreens {

    @Serializable
    data object Home : ConfigurationsScreens()

}
