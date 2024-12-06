package ir.bit24.stations.data.remote.datasource

import ir.bit24.stations.data.remote.model.StationDto

interface StationRemoteDataSource {
    suspend fun fetchStations(): List<StationDto>
}