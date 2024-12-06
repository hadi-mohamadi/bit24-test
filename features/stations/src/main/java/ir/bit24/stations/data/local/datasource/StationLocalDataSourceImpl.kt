package ir.bit24.stations.data.local.datasource

import ir.bit24.stations.data.local.dao.StationDao
import ir.bit24.stations.data.local.entity.StationEntity

class StationLocalDataSourceImpl(private val stationDao: StationDao) : StationLocalDataSource {
    override suspend fun saveStations(stations: List<StationEntity>) {
        stationDao.insertStations(stations)
    }

    override suspend fun getStations(): List<StationEntity> {
        return stationDao.getStations()
    }

}