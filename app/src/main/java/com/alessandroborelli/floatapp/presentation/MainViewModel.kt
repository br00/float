package com.alessandroborelli.floatapp.presentation

import androidx.lifecycle.viewModelScope
import com.alessandroborelli.floatapp.domain.model.Result
import com.alessandroborelli.floatapp.base.BaseViewModel
import com.alessandroborelli.floatapp.domain.model.Mooring
import com.alessandroborelli.floatapp.domain.usecase.AddMooringUseCase
import com.alessandroborelli.floatapp.domain.usecase.GetMooringsUseCase
import com.alessandroborelli.floatapp.domain.usecase.UpdateMooringUseCase
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


internal class MainViewModel @Inject constructor(
    private val mooringsUseCase: GetMooringsUseCase,
    private val addMooringUseCase: AddMooringUseCase,
    private val updateMooringUseCase: UpdateMooringUseCase
) : BaseViewModel<MainUiState, MainUiEvent>(MainUiState.initial()) {

    init {
        retrieveMoorings(state.value)
    }

    override fun reduce(event: MainUiEvent, state: MainUiState) {
        when (event) {
            is MainUiEvent.ShowHome -> {
                retrieveMoorings(state)
            }
            is MainUiEvent.AddMooring -> {
                addMooring(state)
            }
            is MainUiEvent.LeaveMooring -> {
                updateMooring(event.item, state)
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

    private fun addMooring(state: MainUiState) {
        viewModelScope.launch {
            val params = AddMooringUseCase.Params(
                boatId = "Y18x809Swyf3lSnYZzzJ",
                name = "test",
                index = state.data.last().index+1,
                creationDate = Timestamp(Date()),
                arrivedOn = "",
                leftOn = "",
                longitude = 0.0,
                latitude = 0.0,
                notes = ""
            )
            addMooringUseCase(params).collect { result ->
                when(result) {
                    is Result.Loading -> {
                        setState(
                            state.copy(
                                isLoading = true,
                            )
                        )
                    }
                    is Result.Success -> {
                        retrieveMoorings(state)
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

    private fun updateMooring(mooring: Mooring, state: MainUiState) {
        viewModelScope.launch {
            val params = UpdateMooringUseCase.Params(
                boatId = "Y18x809Swyf3lSnYZzzJ",
                id = mooring.id,
                leftOn = Timestamp(Date()),
                lastUpdate = Timestamp(Date())
            )
            updateMooringUseCase(params).collect { result ->
                when(result) {
                    is Result.Loading -> {
                        setState(
                            state.copy(
                                isLoading = true,
                            )
                        )
                    }
                    is Result.Success -> {
                        retrieveMoorings(state)
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