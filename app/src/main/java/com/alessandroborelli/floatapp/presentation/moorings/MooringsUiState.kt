package com.alessandroborelli.floatapp.presentation.moorings

import com.alessandroborelli.floatapp.base.UiState
import com.alessandroborelli.floatapp.domain.model.Mooring

data class MooringsUiState (
    val isLoading: Boolean,
    val data: List<Mooring>
) : UiState {
    companion object {
        fun initial() =
            MooringsUiState(
                isLoading = false,
                data = emptyList()
            )
    }
}