package ir.bit24.stations.presentaion

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.mockk
import ir.bit24.stations.domain.model.GeocodedColumn
import ir.bit24.stations.domain.model.Station
import ir.bit24.stations.domain.usecase.GetStationsUseCase
import ir.bit24.stations.presentation.list.StationState
import ir.bit24.stations.presentation.list.StationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StationViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getStationsUseCase: GetStationsUseCase
    private lateinit var viewModel: StationViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getStationsUseCase = mockk()
        viewModel = StationViewModel(getStationsUseCase)
    }

    @Test
    fun `fetchStations should emit Loading and then Success when use case returns data`() =
        runTest {
            val stationList = listOf(
                Station(
                    id = "1",
                    name = "Finders Street Station",
                    rentalMethod = "CREDITCARD,KEY",
                    capacity = 30,
                    geocodedColumn = GeocodedColumn(-37.818078, longitude = 144.967453)
                ), Station(
                    id = "1",
                    name = "Finders Street Station",
                    rentalMethod = "CREDITCARD,KEY",
                    capacity = 30,
                    geocodedColumn = GeocodedColumn(-37.818078, longitude = 144.967453)
                )
            )
            coEvery { getStationsUseCase(any()) } coAnswers {
                delay(3000)
                stationList
            }

            viewModel.updateFilter("")
            delay(1000)
            val loadingState = viewModel.state.value
            assertTrue(loadingState is StationState.Loading)
            advanceUntilIdle()

            val successState = viewModel.state.value
            assertTrue(successState is StationState.Success)
            assertEquals(stationList, (successState as StationState.Success).stations)
        }

    @Test
    fun `fetchStations should emit Loading and then Error when use case throws exception`() =
        runTest {
            val errorMessage = "Failed to load stations"
            coEvery { getStationsUseCase(any()) } throws RuntimeException(errorMessage)

            viewModel.updateFilter("")

            advanceUntilIdle()

            val state = viewModel.state.value
            assertTrue(state is StationState.Error)
            assertEquals(errorMessage, (state as StationState.Error).message)
        }

    @Test
    fun `updateFilter should update the filter and fetchStations`() = runTest {

        val stationList = listOf(
            Station(
                id = "1",
                name = "Finders Street Station",
                rentalMethod = "CREDITCARD,KEY",
                capacity = 30,
                geocodedColumn = GeocodedColumn(-37.818078, longitude = 144.967453)
            ), Station(
                id = "1",
                name = "Finders Street Station",
                rentalMethod = "CREDITCARD,KEY",
                capacity = 30,
                geocodedColumn = GeocodedColumn(-37.818078, longitude = 144.967453)
            )
        )
        coEvery { getStationsUseCase("filter") } returns stationList

        viewModel.updateFilter("filter")
        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is StationState.Success)
        assertEquals(stationList, (state as StationState.Success).stations)
        assertEquals("filter", viewModel.filter)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}