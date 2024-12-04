package ir.bit24.stations.presentation

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
    data object None : StationState()
    data object Loading : StationState()
    data class Success(val stations: List<Station>) : StationState()
    data class Error(val message: String) : StationState()
}

@HiltViewModel
class StationViewModel @Inject constructor(
    private val getStationsUseCase: GetStationsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<StationState>(StationState.None)
    val state: StateFlow<StationState> = _state

    fun fetchStations() {
        viewModelScope.launch {
            runCatching {
                _state.value = StationState.Loading
                getStationsUseCase()
            }.onSuccess {
                _state.value = StationState.Success(it)
            }.onFailure {
                _state.value = StationState.Error(it.message ?: "Unknown error")
            }
        }
    }
}