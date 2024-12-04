package ir.bit24.hadi.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.bit24.hadi.ui.Destinations.MAIN_LIST
import ir.bit24.stations.presentation.MainScreen


object Destinations {
    const val MAIN_LIST = "MAIN"
}

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MAIN_LIST
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(MAIN_LIST) {
           MainScreen()
        }
    }
}

@Preview
@Composable
fun NavGraphPreview() {
    NavGraph()
}