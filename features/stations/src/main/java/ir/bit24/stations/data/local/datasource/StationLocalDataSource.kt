package ir.bit24.stations.data.local.datasource

import ir.bit24.stations.data.local.entity.StationEntity

interface StationLocalDataSource {
    suspend fun saveStations(stations: List<StationEntity>)
    suspend fun getStations(): List<StationEntity>
}
