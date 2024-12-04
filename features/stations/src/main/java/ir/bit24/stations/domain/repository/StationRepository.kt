package ir.bit24.stations.domain.repository

import ir.bit24.stations.domain.model.Station

interface StationRepository {
    suspend fun getStations(): List<Station>
}