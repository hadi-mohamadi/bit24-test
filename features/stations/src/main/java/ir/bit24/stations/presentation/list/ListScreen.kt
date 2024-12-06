package ir.bit24.stations.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ir.bit24.stations.domain.model.GeocodedColumn
import ir.bit24.stations.domain.model.Station
import ir.bit24.stations.presentation.util.capacity
import ir.bit24.stations.presentation.util.details
import ir.bit24.stations.presentation.util.latitude
import ir.bit24.stations.presentation.util.longitude
import ir.bit24.stations.presentation.util.rentalMethod
import ir.bit24.stations.presentation.util.showInMap

@Composable
fun ListScreen(modifier: Modifier = Modifier, navigateToStationDetail: (Station) -> Unit) {
    val viewModel: StationViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        when (state.value) {
            is StationState.Loading -> {
                LoadingContent()
            }

            is StationState.Error -> {
                ErrorContent(message = (state.value as StationState.Error).message)
            }

            is StationState.Success -> {
                MainContent(
                    stations = (state.value as StationState.Success).stations,
                    onShowInMapClick = {},
                    onDetailsClick = { station -> navigateToStationDetail(station) })
            }
        }
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier, stations: List<Station>, onDetailsClick: (Station) -> Unit,
    onShowInMapClick: (Station) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.weight(1F))
        LazyRow {
            itemsIndexed(stations, key = { _, item -> item.id }) { _, station ->
                StationCard(
                    station = station,
                    onDetailsClick = { onDetailsClick(station) },
                    onShowInMapClick = { onShowInMapClick(station) })
            }
        }
    }
}

@Preview
@Composable
fun MainContentPreview() {
    MainContent(
        stations = listOf(
            Station(
                id = "1",
                name = "Finders Street Station",
                rentalMethod = "CREDITCARD,KEY",
                capacity = 30,
                geocodedColumn = GeocodedColumn("-37.818078", longitude = "144.967453")
            ), Station(
                id = "1",
                name = "Finders Street Station",
                rentalMethod = "CREDITCARD,KEY",
                capacity = 30,
                geocodedColumn = GeocodedColumn("-37.818078", longitude = "144.967453")
            )
        ), onDetailsClick = {}, onShowInMapClick = {}
    )
}

@Composable
fun StationCard(
    modifier: Modifier = Modifier, station: Station, onDetailsClick: () -> Unit,
    onShowInMapClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Text(
                text = station.name,
                style = MaterialTheme.typography.titleLarge,
                color = colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "$rentalMethod ${station.rentalMethod}",
                style = MaterialTheme.typography.bodyLarge,
                color = colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))


            Box(
                modifier = Modifier
                    .background(colorScheme.secondary, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "$capacity ${station.capacity}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onSecondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))


            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "$latitude ${station.geocodedColumn.latitude}",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorScheme.onBackground
                )
                Text(
                    text = "$longitude ${station.geocodedColumn.longitude}",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { onDetailsClick() },
                    modifier = Modifier
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = details, color = colorScheme.onPrimary)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { onShowInMapClick() },
                    modifier = Modifier
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = showInMap, color = colorScheme.onPrimary)
                }
            }
        }
    }
}

@Preview
@Composable
fun StationCardPreview() {
    StationCard(
        station = Station(
            id = "1",
            name = "Finders Street Station",
            rentalMethod = "CREDITCARD,KEY",
            capacity = 30,
            geocodedColumn = GeocodedColumn("-37.818078", longitude = "144.967453")
        ), onDetailsClick = {}, onShowInMapClick = {}
    )
}


@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun LoadingContentPreview() {
    LoadingContent()
}

@Composable
fun ErrorContent(modifier: Modifier = Modifier, message: String) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(message, color = colorScheme.error)
    }
}

@Preview
@Composable
fun ErrorContentPreview() {
    ErrorContent(message = "This is an error")
}