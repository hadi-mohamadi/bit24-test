package ir.bit24.stations.data.repository

import ir.bit24.stations.data.local.datasource.StationLocalDataSource
import ir.bit24.stations.data.local.entity.StationEntity
import ir.bit24.stations.data.remote.datasource.StationRemoteDataSource
import ir.bit24.stations.domain.model.GeocodedColumn
import ir.bit24.stations.domain.model.Station
import ir.bit24.stations.domain.repository.StationRepository

class StationRepositoryImpl(
    private val remoteDataSource: StationRemoteDataSource,
    private val localDataSource: StationLocalDataSource
) : StationRepository {
    override suspend fun getStations(): List<Station> {
        return try {
            val stationsFromApi = remoteDataSource.fetchStations()
            val stationEntities = stationsFromApi.map {
                StationEntity(
                    id = it.stationId,
                    name = it.name,
                    rentalMethod = it.rentalMethod,
                    capacity = it.capacity.toInt(),
                    latitude = it.geocodedColumn.latitude,
                    longitude = it.geocodedColumn.longitude
                )
            }
            localDataSource.saveStations(stationEntities)
            stationsFromApi.map { it.toDomainModel() }
        } catch (e: Exception) {
            return localDataSource.getStations().map {
                Station(
                    id = it.id,
                    name = it.name,
                    rentalMethod = it.rentalMethod,
                    capacity = it.capacity,
                    geocodedColumn = GeocodedColumn(
                        latitude = it.latitude.toDouble(),
                        longitude = it.longitude.toDouble()
                    )
                )
            }
        }
    }
}