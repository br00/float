package com.alessandroborelli.floatapp.presentation

import androidx.lifecycle.viewModelScope
import com.alessandroborelli.floatapp.data.model.Result
import com.alessandroborelli.floatapp.base.BaseViewModel
import com.alessandroborelli.floatapp.domain.usecase.GetMooringsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val mooringsUseCase: GetMooringsUseCase
) : BaseViewModel<MainUiState, MainUiEvent>(MainUiState.initial()) {

    init {
        retrieveMoorings(state.value)
    }

    override fun reduce(event: MainUiEvent, state: MainUiState) {
        when (event) {
            is MainUiEvent.ShowHome -> {
                retrieveMoorings(state)
            }
        }
    }

    private fun retrieveMoorings(state: MainUiState) {
        viewModelScope.launch {
            mooringsUseCase("Y18x809Swyf3lSnYZzzJ").collect { result ->
                when (result) {
                    is Result.Loading -> {
                        setState(
                            state.copy(
                                isLoading = true,
                            )
                        )
                    }
                    is Result.Success -> {
                        setState(
                            state.copy(
                                isLoading = false,
                                data = result.data.data
                            )
                        )
                    }
                    is Result.Failed -> {
                        //TODO error message here
                        setState(
                            state.copy(
                                isLoading = false,
                            )
                        )
                    }
                }
            }
        }
    }

}