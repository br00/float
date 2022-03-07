package com.alessandroborelli.floatapp.presentation

import com.alessandroborelli.floatapp.base.UiEvent

sealed class MainUiEvent : UiEvent {
    object ShowHome : MainUiEvent()
    object AddMooring : MainUiEvent()
}