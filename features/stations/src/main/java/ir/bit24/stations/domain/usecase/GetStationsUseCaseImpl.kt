package ir.bit24.stations.domain.usecase

import ir.bit24.stations.domain.model.Station
import ir.bit24.stations.domain.repository.StationRepository
import javax.inject.Inject

class GetStationsUseCaseImpl @Inject constructor(
    private val repository: StationRepository
) : GetStationsUseCase {
    override suspend operator fun invoke(): List<Station> {
        return repository.getStations()
    }
}