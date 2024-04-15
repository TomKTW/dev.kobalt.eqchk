package dev.kobalt.eqchk.android.main

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.kobalt.eqchk.android.details.DetailsScreen
import dev.kobalt.eqchk.android.filter.FilterScreen
import dev.kobalt.eqchk.android.home.HomeScreen

@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = MainDestinations.HOME
    ) {
        composable(
            route = MainDestinations.HOME
        ) { _ ->
            HomeScreen(
                navigateToDetails = { navController.navigate(MainDestinations.DETAILS.replace("{id}", it)) },
                navigateToFilter = { navController.navigate(MainDestinations.FILTER) }
            )
        }
        composable(
            route = MainDestinations.FILTER
        ) { _ ->
            FilterScreen(
                navigateBack = { if (navController.previousBackStackEntry != null) navController.popBackStack() }
            )
        }
        composable(
            route = MainDestinations.DETAILS,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )
        ) { _ ->
            DetailsScreen(
                navigateBack = {
                    if (navController.previousBackStackEntry != null) navController.popBackStack()
                },
                navigateOpen = {
                    context.startActivity(Intent(Intent.ACTION_VIEW, it))
                }
            )
        }
    }
}


