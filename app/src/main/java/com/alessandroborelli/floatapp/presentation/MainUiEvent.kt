package com.alessandroborelli.floatapp.presentation

import com.alessandroborelli.floatapp.base.UiEvent
import com.alessandroborelli.floatapp.domain.model.Mooring

sealed class MainUiEvent : UiEvent {
    object ShowHome : MainUiEvent()
    data class LeaveMooring(val item: Mooring) : MainUiEvent()
    object AddMooring : MainUiEvent()
}