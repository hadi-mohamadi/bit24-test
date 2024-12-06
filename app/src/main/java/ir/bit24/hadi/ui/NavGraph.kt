package ir.bit24.hadi.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import ir.bit24.hadi.ui.Destinations.STATION_DETAIL_LIST
import ir.bit24.hadi.ui.Destinations.STATION_LIST
import ir.bit24.stations.domain.model.Station
import ir.bit24.stations.presentation.detail.StationDetailScreen
import ir.bit24.stations.presentation.list.ListScreen


object Destinations {
    const val STATION_LIST = "STATION_LIST"
    const val STATION_DETAIL_LIST = "STATION_DETAIL_LIST"
}

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = STATION_LIST
) {
    val actions = remember(navController) { MainActions(navController) }
    NavHost(modifier = modifier, navController = navController, startDestination = startDestination) {
        composable(STATION_LIST) {
            ListScreen(navigateToStationDetail = actions.navigateToStationDetail)
        }

        composable(
            route = "$STATION_DETAIL_LIST/{station}",
            arguments = listOf(navArgument("station") { type = NavType.StringType })
        ) { backStackEntry ->
            val stationJson = backStackEntry.arguments?.getString("station")
            val station = Gson().fromJson(stationJson, Station::class.java)
            StationDetailScreen(station = station, onBackButtonClicked = {navController.navigateUp()})
        }
    }
}

@Preview
@Composable
fun NavGraphPreview() {
    NavGraph()
}


class MainActions(navController: NavHostController) {

    val navigateToStationDetail: (Station) -> Unit = { station ->
        val stationJson = Gson().toJson(station)
        navController.navigate("$STATION_DETAIL_LIST/$stationJson")
    }
}