package ir.bit24.stations.domain.usecase

import ir.bit24.stations.domain.model.Station
import ir.bit24.stations.domain.repository.StationRepository

class GetStationsUseCaseImpl(
    private val repository: StationRepository
) : GetStationsUseCase {
    override suspend operator fun invoke(): List<Station> {
        return repository.getStations()
        return emptyList()
    }
}