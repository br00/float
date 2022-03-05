package com.alessandroborelli.floatapp.presentation

import com.alessandroborelli.floatapp.base.UiState
import com.alessandroborelli.floatapp.domain.model.Mooring

data class MainUiState (
    val isLoading: Boolean,
    val data: List<Mooring>
) : UiState {
    companion object {
        fun initial() =
            MainUiState(
                isLoading = false,
                data = emptyList()
            )
    }
}