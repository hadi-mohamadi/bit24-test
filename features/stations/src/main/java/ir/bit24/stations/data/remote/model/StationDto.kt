package ir.bit24.stations.data.remote.model

import ir.bit24.stations.domain.model.Station
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StationDto(
    @SerialName("station_id")
    val stationId: String,
    val name: String,
    @SerialName("rental_method")
    val rentalMethod: String,
    val capacity: String,
    @SerialName("geocoded_column")
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
