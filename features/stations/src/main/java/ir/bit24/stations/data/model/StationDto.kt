package ir.bit24.stations.data.model

import ir.bit24.stations.domain.model.Station
import kotlinx.serialization.Serializable

@Serializable
data class StationDto(
    val stationId: String,
    val name: String,
    val rentalMethod: String,
    val capacity: String,
    val geocodedColumn: GeocodedColumnDto
) {
    fun toDomainModel(): Station {
        return Station(
            id = this.stationId,
            name = this.name,
            rentalMethod = this.rentalMethod,
            capacity = this.capacity.toInt(),
            geocodedColumn = this.geocodedColumn.toDomainModel()
        )
    }
}
