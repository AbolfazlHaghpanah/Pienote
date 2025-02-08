rootProject.name = "Pienote"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.myket.ir")
        }
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("https://maven.myket.ir")
        }
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":app")
include(":data")
include(":texteditor")
include(":domain")
includeBuild("build-logic")
include(":ui:feature-home")
include(":ui:feature-note")
include(":ui:feature-category")
include(":ui:design-system")
include(":ui:navigation")
include(":ui:base")
include(":ui:snackbar")
include(":ui:swipe-handler")
