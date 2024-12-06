package ir.bit24.stations.data.datasource

import ir.bit24.stations.data.model.StationDto

interface StationRemoteDataSource {
    suspend fun fetchStations(): List<StationDto>
}