package com.alessandroborelli.floatapp.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<S : UiState, in E : UiEvent>(initialVal: S) : ViewModel() {

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialVal)

    val state: StateFlow<S>
        get() = _state

    protected fun setState(newState: S) {
        val success = _state.tryEmit(newState)
    }

    protected fun onEvent(event: E) {
        reduce(event, _state.value)
    }

    abstract fun reduce(event: E, state: S)
}