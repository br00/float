package com.alessandroborelli.floatapp.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessandroborelli.floatapp.data.model.Owner
import com.alessandroborelli.floatapp.data.model.State
import com.alessandroborelli.floatapp.data.repository.OwnersRepository
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val ownersRepository: OwnersRepository
) : ViewModel() {

    //TODO make generic state
    val ownerState = mutableStateOf("")

    init {
        viewModelScope.launch {
            ownersRepository.getOwnerDetails("4bbvhbafj4Ewsu5jAI6N").collect { state ->
                when (state) {
                    is State.Loading -> {
                        ownerState.value = "loading.."
                    }
                    is State.Success -> {
                        ownerState.value = state.data.fullName ?: ""
                    }
                    is State.Failed -> {
                        ownerState.value = "Ops! Something went wrong"
                    }
                }
            }
        }
    }

}