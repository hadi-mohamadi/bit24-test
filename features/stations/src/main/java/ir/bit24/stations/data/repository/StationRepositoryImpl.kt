package ir.bit24.stations.data.repository

import ir.bit24.stations.data.datasource.StationRemoteDataSource
import ir.bit24.stations.domain.model.Station
import ir.bit24.stations.domain.repository.StationRepository

class StationRepositoryImpl(
    private val remoteDataSource: StationRemoteDataSource
) : StationRepository {
    override suspend fun getStations(): List<Station> {
        return remoteDataSource.fetchStations().map { it.toDomainModel() }
    }
}