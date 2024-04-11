package dev.kobalt.eqchk.android.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import dev.kobalt.eqchk.android.details.DetailsScreen
import dev.kobalt.eqchk.android.details.DetailsScreenArgument
import dev.kobalt.eqchk.android.home.HomeScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme {
                MainNavHost()
            }
        }
    }
}

@Composable
fun MainTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content
    )
}

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
                navigateToSearch = { navController.navigate(MainDestinations.SEARCH) }
            )
        }
        composable(
            route = MainDestinations.DETAILS,
            arguments = listOf(
                navArgument(DetailsScreenArgument.ID) { type = NavType.StringType }
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
        composable(
            route = MainDestinations.SEARCH
        ) { _ ->
            SearchRoute()
        }
    }
}

object MainDestinations {
    const val HOME = "home"
    const val DETAILS = "details?id={id}"
    const val SEARCH = "search"
}

