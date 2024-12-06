package ir.bit24.stations.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.bit24.stations.domain.model.Station
import ir.bit24.stations.domain.usecase.GetStationsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class StationState {
    data object Loading : StationState()
    data class Success(val stations: List<Station>) : StationState()
    data class Error(val message: String) : StationState()
}

@HiltViewModel
class StationViewModel @Inject constructor(
    private val getStationsUseCase: GetStationsUseCase
) : ViewModel() {
    private val _state:MutableStateFlow<StationState> = MutableStateFlow<StationState>(StationState.Loading)
    val state: StateFlow<StationState> get() = _state
    init {
        fetchStations()
    }

    private fun fetchStations() {
        viewModelScope.launch {
            runCatching {
                _state.value = StationState.Loading
                getStationsUseCase()
            }.onSuccess {
                _state.value = StationState.Success(it)
            }.onFailure {
                println("hellooo: ${it.message}")
                _state.value = StationState.Error(it.message ?: "Unknown error")
            }
        }
    }
}