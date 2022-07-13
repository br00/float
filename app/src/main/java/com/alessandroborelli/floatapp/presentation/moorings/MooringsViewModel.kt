package com.alessandroborelli.floatapp.presentation.moorings

import androidx.lifecycle.viewModelScope
import com.alessandroborelli.floatapp.domain.model.Result
import com.alessandroborelli.floatapp.base.BaseViewModel
import com.alessandroborelli.floatapp.data.Constants
import com.alessandroborelli.floatapp.domain.model.Location
import com.alessandroborelli.floatapp.domain.model.Mooring
import com.alessandroborelli.floatapp.domain.usecase.AddMooringUseCase
import com.alessandroborelli.floatapp.domain.usecase.GetMooringsUseCase
import com.alessandroborelli.floatapp.domain.usecase.UpdateMooringUseCase
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
internal class MooringsViewModel @Inject constructor(
    private val mooringsUseCase: GetMooringsUseCase,
    private val addMooringUseCase: AddMooringUseCase,
    private val updateMooringUseCase: UpdateMooringUseCase
) : BaseViewModel<MooringsUiState, MooringsUiEvent>(MooringsUiState.initial()) {

    //TODO move to ui state
    private val cameraPosition = MutableStateFlow<Location?>(null)

    init {
        retrieveMoorings(state.value)
    }

    override fun reduce(event: MooringsUiEvent, state: MooringsUiState) {
        when (event) {
            is MooringsUiEvent.AddMooring -> {
                addMooring(event.item, state)
            }
            is MooringsUiEvent.LeaveMooring -> {
                updateMooring(event.item, state)
            }
            is MooringsUiEvent.UpdateMapLocation -> {
                updateMapLocation(event.location, state)
            }
        }
    }

    fun setCameraPosition(l: Location) {
        if (l != cameraPosition.value) {
            cameraPosition.value = l
        }
    }

    private fun updateMapLocation(location: Location, state: MooringsUiState) {
        setState(
            state.copy(
                selectedMapLocation = location
            )
        )
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
                        val currentMooring = result.data.data.find { it.leftOn.isEmpty() }
                        val initialMapLocation =
                            if (currentMooring?.latitude != null) {
                                Location(currentMooring.latitude, currentMooring.longitude)
                            } else {
                                Constants.LONDON
                            }
                        setState(
                            state.copy(
                                isLoading = false,
                                data = result.data.data,
                                selectedMapLocation = initialMapLocation
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

    private fun addMooring(mooring: Mooring, state: MooringsUiState) {
        viewModelScope.launch {
            val params = AddMooringUseCase.Params(
                boatId = "Y18x809Swyf3lSnYZzzJ",
                name = "test", //TODO put 3words name
                index = state.data.last().index+1, //TODO index is buggy
                creationDate = Timestamp(Date()),
                arrivedOn = mooring.arrivedOn,
                leftOn = mooring.leftOn,
                latitude = mooring.latitude,
                longitude = mooring.longitude
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