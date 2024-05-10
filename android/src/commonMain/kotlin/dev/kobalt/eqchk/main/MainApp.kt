package dev.kobalt.eqchk.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.kobalt.eqchk.android.details.DetailsScreen
import dev.kobalt.eqchk.android.details.DetailsViewModel
import dev.kobalt.eqchk.android.event.EventApi
import dev.kobalt.eqchk.android.event.EventDao
import dev.kobalt.eqchk.android.event.EventEntity
import dev.kobalt.eqchk.android.event.EventRepository
import dev.kobalt.eqchk.android.filter.FilterScreen
import dev.kobalt.eqchk.home.HomeScreen
import dev.kobalt.eqchk.home.HomeViewModel
import io.ktor.client.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    val repository = EventRepository(
        EventApi(HttpClient()),
        object : EventDao {
            override fun selectAll(): Flow<List<EventEntity>> {
                return MutableStateFlow(emptyList())
            }

            override fun selectItemFlow(id: String): Flow<EventEntity?> {
                return MutableStateFlow(null)
            }

            override suspend fun insertAll(events: List<EventEntity>) {
            }

            override suspend fun deleteAll() {
            }

            override suspend fun insert(event: EventEntity) {
            }

            override suspend fun delete(id: String) {
            }

        }
    )

    NavHost(
        navController = navController,
        startDestination = MainDestinations.HOME
    ) {
        composable(
            route = MainDestinations.HOME
        ) { _ ->
            HomeScreen(
                viewModel = HomeViewModel(
                    repository
                ),
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
                viewModel = DetailsViewModel(
                    repository
                ),
                navigateBack = {
                    if (navController.previousBackStackEntry != null) navController.popBackStack()
                },
                navigateOpen = {
                    launchBrowser(it)
                }
            )
        }
    }
}

/* expect */ fun launchBrowser(url: String) { /* TODO */
}