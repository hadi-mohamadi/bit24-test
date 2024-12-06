package ir.bit24.stations.domain.usecase

import ir.bit24.stations.domain.model.Station
import ir.bit24.stations.domain.repository.StationRepository

class GetStationsUseCaseImpl(
    private val repository: StationRepository
) : GetStationsUseCase {
    override suspend operator fun invoke(name: String): List<Station> {
        val stations = repository.getStations()
        return if (name.isEmpty()) stations else stations.filter {
            it.name.contains(name, ignoreCase = true)
        }
    }
}