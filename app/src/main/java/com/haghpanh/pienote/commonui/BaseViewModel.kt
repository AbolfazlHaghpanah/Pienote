package com.haghpanh.pienote.commonui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.haghpanh.pienote.commonui.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0

/**
 * Base class for ViewModels in a Compose-based application.
 * Provides utilities for managing the state of the UI using coroutines and flows.
 *
 * @param initialState The initial state of the ViewModel.
 */
abstract class BaseViewModel<ViewState>(
    initialState: ViewState,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    private val state = _state.asStateFlow()

    /**
     * Get the current state of the ViewModel.
     *
     * @return The current state.
     */
    protected fun getCurrentState() = _state.value

    /**
     * Update the state of the ViewModel based on a transformation function.
     *
     * @param dispatcher The coroutine dispatcher to use for the update operation. Default is [Dispatchers.IO].
     * @param copy A function that computes the new state based on the current state.
     */
    protected fun updateState(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        copy: (ViewState) -> ViewState
    ) {
        viewModelScope.launch(dispatcher) {
            val newState = copy(getCurrentState())

            _state.emit(newState)
        }
    }

    /**
     * Update the state of the ViewModel based on a transformation function and
     * invoke a callback when the state is updated.
     *
     * @param dispatcher The coroutine dispatcher to use for the update operation. Default is [Dispatchers.IO].
     * @param onUpdated A callback function to invoke after the state is updated.
     * @param copy A function that computes the new state based on the current state.
     */
    protected fun updateState(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        onUpdated: (suspend () -> Unit)? = null,
        copy: (ViewState) -> ViewState
    ) {
        viewModelScope.launch(dispatcher) {
            val newState = copy(getCurrentState())
            _state.emit(newState)

            onUpdated?.invoke()
        }
    }

    // TODO Add KDoc Later
    @Composable
    fun <T> HandleResult(
        prop: KProperty0<Result<T>>,
        onSuccess: (suspend (T) -> Unit)? = null,
        onFail: (suspend (Throwable) -> Unit)? = null,
    ) {
        LaunchedEffect(prop.get()) {
            if (prop.get() is Result.Success && onSuccess != null) {
                onSuccess(prop.get().value!!)
            } else if (prop.get() is Result.Fail && onFail != null) {
                onFail(prop.get().value as Throwable)
            }
        }
    }

    /**
     *gives access to SavedStateHandle in compose Screen or viewModels that needs instance of it.
     *
     * @param key the key of value in savedStateHandle
     * @return get value with given key from savedStateHandle
     */
    fun <T> savedStateHandler(key: String): T? =
        savedStateHandle.get<T>(key)

    /**
     * Collect the state as a Compose [State] with lifecycle awareness.
     *
     * @return The Compose [State] representing the current state of the ViewModel.
     */
    @Composable
    fun collectAsStateWithLifecycle(): State<ViewState> =
        state.collectAsStateWithLifecycle()

    /**
     * Operator function to simplify getting the value from a Compose [State].
     *
     * @param thisObj The object receiving the property.
     * @param property The property being accessed.
     * @return The value of the [State].
     */
    operator fun <ViewState> State<ViewState>.getValue(
        thisObj: Any?,
        property: KProperty<*>
    ): ViewState = value
}
