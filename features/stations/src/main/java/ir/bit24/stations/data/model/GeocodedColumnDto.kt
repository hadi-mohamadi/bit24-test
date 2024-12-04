package ir.bit24.stations.data.model

import ir.bit24.stations.domain.model.GeocodedColumn
import kotlinx.serialization.Serializable

@Serializable
data class GeocodedColumnDto(
    val latitude: String,
    val longitude: String
) {
    fun toDomainModel(): GeocodedColumn {
        return GeocodedColumn(latitude = this.latitude, longitude = longitude)
    }
}
