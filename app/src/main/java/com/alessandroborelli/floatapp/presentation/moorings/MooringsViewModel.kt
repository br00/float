package com.alessandroborelli.floatapp.presentation.moorings

import androidx.lifecycle.viewModelScope
import com.alessandroborelli.floatapp.domain.model.Result
import com.alessandroborelli.floatapp.base.BaseViewModel
import com.alessandroborelli.floatapp.domain.model.Location
import com.alessandroborelli.floatapp.domain.model.Mooring
import com.alessandroborelli.floatapp.domain.usecase.AddMooringUseCase
import com.alessandroborelli.floatapp.domain.usecase.GetMooringsUseCase
import com.alessandroborelli.floatapp.domain.usecase.UpdateMooringUseCase
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
internal class MooringsViewModel @Inject constructor(
    private val mooringsUseCase: GetMooringsUseCase,
    private val addMooringUseCase: AddMooringUseCase,
    private val updateMooringUseCase: UpdateMooringUseCase
) : BaseViewModel<MooringsUiState, MooringsUiEvent>(MooringsUiState.initial()) {

    private val london = Location(51.525493,-0.0822173)
    val location = MutableStateFlow<Location>(london)

    val cameraPosition = MutableStateFlow<Location?>(null)

    init {
        retrieveMoorings(state.value)
    }

    override fun reduce(event: MooringsUiEvent, state: MooringsUiState) {
        when (event) {
            is MooringsUiEvent.AddMooring -> {
                addMooring(state)
            }
            is MooringsUiEvent.LeaveMooring -> {
                updateMooring(event.item, state)
            }
        }
    }

    fun setLocation(l: Location) {
        location.value = l
    }

    fun setCameraPosition(l: Location) {
        if (l != cameraPosition.value) {
            cameraPosition.value = l
        }
    }

    private fun retrieveMoorings(state: MooringsUiState) {
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

    private fun addMooring(state: MooringsUiState) {
        viewModelScope.launch {
            val params = AddMooringUseCase.Params(
                boatId = "Y18x809Swyf3lSnYZzzJ",
                name = "test",
                index = state.data.last().index+1,
                creationDate = Timestamp(Date()),
                arrivedOn = Timestamp(Date())
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

    private fun updateMooring(mooring: Mooring, state: MooringsUiState) {
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