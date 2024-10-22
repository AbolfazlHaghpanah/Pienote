package pienote.commonui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pienote.commonui.utils.annotation.EffectState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.reflect.KProperty
import kotlin.reflect.full.hasAnnotation

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
     * Safely updates the current state of the ViewModel by applying a transformation to it.
     *
     * Example usage:
     * ```
     * updateState { copy(property = newValue) }
     * ```
     * @param copy A lambda function that takes the current state and returns the new transformed state.
     */
    fun updateState(
        copy: ViewState.() -> ViewState
    ) {
        _state.update(copy)
    }

    /**
     * gives access to SavedStateHandle in compose Screen or viewModels that needs instance of it.
     *
     * @param key the key of value in savedStateHandle
     * @return get value with given key from savedStateHandle
     */
    fun <T> savedStateHandler(key: String): T? =
        savedStateHandle.get<T>(key)

    /**
     * A composable function that handles the disposal of effects by resetting the properties annotated
     * with `@EffectState` in the current ViewModel state to their initial values when the composable is disposed.
     *
     * This function uses reflection to access the primary constructor of the state class, retrieves the initial values
     * of the properties, and creates a new state instance with the updated values upon disposal.
     *
     * This approach is useful in scenarios where certain state properties should be reset to their initial values
     * when a composable is removed from the composition, ensuring that transient effects do not persist.
     *
     * @throws IllegalStateException If the current state is null or if no constructor is found for the state class.
     */
    @SuppressLint("ComposableNaming")
    @Composable
    fun handleEffectsDispose() {
        DisposableEffect(Unit) {
            val constructor = state.value!!::class
                .constructors
                .first()

            onDispose {
                val args = constructor
                    .parameters
                    .associateWith { param ->
                        if (param.hasAnnotation<EffectState>()) {
                            null
                        } else {
                            state.value!!::class.members
                                .find { it.name == param.name }
                                ?.call(state.value)
                        }
                    }

                val newState = state.value!!::class
                    .constructors
                    .first()
                    .callBy(args)

                updateState { newState }
            }
        }
    }

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
