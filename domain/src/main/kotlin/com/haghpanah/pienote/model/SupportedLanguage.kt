package com.haghpanah.pienote.model

enum class SupportedLanguage(val tag: String) {
    En("en-US"),
    Fa("fa")
}

fun getDefaultLanguage() = SupportedLanguage.En
