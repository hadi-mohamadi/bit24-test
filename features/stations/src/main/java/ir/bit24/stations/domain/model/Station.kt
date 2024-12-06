package ir.bit24.stations.domain.model

data class Station(
    val id: String,
    val name: String,
    val rentalMethod: String,
    val capacity: Int,
    val geocodedColumn: GeocodedColumn
)
