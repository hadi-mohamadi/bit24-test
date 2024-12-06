package ir.bit24.stations.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import ir.bit24.stations.data.local.dao.StationDao
import ir.bit24.stations.data.local.database.StationDatabase
import ir.bit24.stations.data.local.datasource.StationLocalDataSource
import ir.bit24.stations.data.local.datasource.StationLocalDataSourceImpl
import ir.bit24.stations.data.remote.datasource.StationRemoteDataSource
import ir.bit24.stations.data.remote.datasource.StationRemoteDataSourceImpl
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
    fun provideStationRepository(
        stationRemoteDataSource: StationRemoteDataSource,
        stationLocalDataSource: StationLocalDataSource
    ): StationRepository {
        return StationRepositoryImpl(stationRemoteDataSource, stationLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideStationRemoteDataSource(httpClient: HttpClient): StationRemoteDataSource {
        return StationRemoteDataSourceImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): StationDatabase {
        return Room.databaseBuilder(context, StationDatabase::class.java, "stations_db").build()
    }

    @Provides
    fun provideStationDao(database: StationDatabase): StationDao {
        return database.stationDao()
    }

    @Provides
    @Singleton
    fun provideStationLocalDataSource(stationDao: StationDao): StationLocalDataSource {
        return StationLocalDataSourceImpl(stationDao)
    }
}