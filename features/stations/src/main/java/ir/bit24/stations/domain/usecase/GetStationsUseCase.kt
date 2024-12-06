package ir.bit24.stations.domain.usecase

import ir.bit24.stations.domain.model.Station

interface GetStationsUseCase {
    suspend operator fun invoke(name: String): List<Station>
}