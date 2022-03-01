package com.alessandroborelli.floatapp.presentation

import com.alessandroborelli.floatapp.base.UiState

data class MainUiState (
    val isLoading: Boolean,
    val data: String?
) : UiState {
    companion object {
        fun initial() =
            MainUiState(
                isLoading = false,
                //TODO add uninitialized here
                data = null
            )
    }
}