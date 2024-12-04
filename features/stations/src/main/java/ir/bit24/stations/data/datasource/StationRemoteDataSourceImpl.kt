package ir.bit24.stations.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ir.bit24.stations.data.model.StationDto
import javax.inject.Inject

class StationRemoteDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient
) : StationRemoteDataSource {
    override suspend fun fetchStations(): List<StationDto> {
        return httpClient.get("00313c22-a4c5-467f-b50c-ebb0fc58c58d") {
            contentType(ContentType.Application.Json)
        }.body()
    }
}