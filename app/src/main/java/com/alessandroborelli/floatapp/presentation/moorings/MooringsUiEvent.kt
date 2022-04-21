package com.alessandroborelli.floatapp.presentation.moorings

import com.alessandroborelli.floatapp.base.UiEvent
import com.alessandroborelli.floatapp.domain.model.Mooring

sealed class MooringsUiEvent : UiEvent {
    data class LeaveMooring(val item: Mooring) : MooringsUiEvent()
    object AddMooring : MooringsUiEvent()
}