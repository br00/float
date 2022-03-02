package com.alessandroborelli.floatapp.presentation

import androidx.lifecycle.viewModelScope
import com.alessandroborelli.floatapp.data.model.Result
import com.alessandroborelli.floatapp.base.BaseViewModel
import com.alessandroborelli.floatapp.data.repository.OwnersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val ownersRepository: OwnersRepository
) : BaseViewModel<MainUiState, MainUiEvent>(MainUiState.initial()) {

    init {
        retrieveOwner(state.value)
    }

    override fun reduce(event: MainUiEvent, state: MainUiState) {
        when (event) {
            is MainUiEvent.ShowHome -> {
                retrieveOwner(state)
            }
        }
    }

    private fun retrieveOwner(state: MainUiState) {
        viewModelScope.launch {
            ownersRepository.getOwnerDetails("CyBu94TFCDiqFXeCq8rc").collect { result ->
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
                                data = result.data.fullName
                            )
                        )
                    }
                    is Result.Failed -> {
                        // error message here
                    }
                }
            }
        }
    }

}