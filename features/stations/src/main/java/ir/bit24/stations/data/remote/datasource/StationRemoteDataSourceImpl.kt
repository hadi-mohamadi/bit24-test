package ir.bit24.stations.data.remote.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ir.bit24.stations.data.remote.model.StationDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StationRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : StationRemoteDataSource {
    override suspend fun fetchStations(): List<StationDto> {
        return withContext(Dispatchers.IO){
            httpClient.get("00313c22-a4c5-467f-b50c-ebb0fc58c58d") {
                contentType(ContentType.Application.Json)
            }.body()
        }
    }
}