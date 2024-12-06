package ir.bit24.stations.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.bit24.stations.data.local.dao.StationDao
import ir.bit24.stations.data.local.entity.StationEntity

@Database(entities = [StationEntity::class], version = 1, exportSchema = false)
abstract class StationDatabase : RoomDatabase() {
    abstract fun stationDao(): StationDao
}
