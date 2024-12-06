package ir.bit24.stations.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import ir.bit24.stations.data.datasource.StationRemoteDataSource
import ir.bit24.stations.data.datasource.StationRemoteDataSourceImpl
import ir.bit24.stations.data.repository.StationRepositoryImpl
import ir.bit24.stations.domain.repository.StationRepository
import ir.bit24.stations.domain.usecase.GetStationsUseCase
import ir.bit24.stations.domain.usecase.GetStationsUseCaseImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class StationsModule {

    @Provides
    @Singleton
    fun provideGetStationsUseCase(stationRepository: StationRepository): GetStationsUseCase {
        return GetStationsUseCaseImpl(stationRepository)
    }

    @Provides
    @Singleton
    fun provideStationRepository(stationRemoteDataSource: StationRemoteDataSource): StationRepository {
        return StationRepositoryImpl(stationRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideStationRemoteDataSource(httpClient: HttpClient): StationRemoteDataSource {
        return StationRemoteDataSourceImpl(httpClient)
    }
}