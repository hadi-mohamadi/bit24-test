package ir.bit24.stations.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stations")
data class StationEntity(
    @PrimaryKey val id: String,
    val name: String,
    val rentalMethod: String,
    val capacity: Int,
    val latitude: String,
    val longitude: String
)