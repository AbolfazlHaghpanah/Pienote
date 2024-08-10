package com.haghpanh.pienote.commonui.utils

sealed interface Result<T> {
    val value: T?

    sealed interface Complete<T : Any?> : Result<T>
    sealed interface Incomplete<T> : Result<T>

    data class Success<T>(override val value: T) : Complete<T>
    data class Fail(override val value: Throwable) : Complete<Throwable>

    data class Idle<T>(override val value: T? = null) : Incomplete<T>
    data class Loading<T>(override val value: T? = null) : Incomplete<T>
}
