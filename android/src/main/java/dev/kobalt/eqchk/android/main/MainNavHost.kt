package dev.kobalt.eqchk.android.main

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.kobalt.eqchk.android.R
import dev.kobalt.eqchk.android.details.DetailsScreen
import dev.kobalt.eqchk.android.details.ImageTitleValueLabel
import dev.kobalt.eqchk.android.home.HomeMapView
import dev.kobalt.eqchk.android.home.HomeScreen
import dev.kobalt.eqchk.android.search.SearchRoute
import java.math.RoundingMode

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

            val openAlertDialog = remember { mutableStateOf(false) }

            HomeScreen(
                navigateToDetails = { navController.navigate(MainDestinations.DETAILS.replace("{id}", it)) },
                navigateToSearch = {
                    openAlertDialog.value = !openAlertDialog.value
                }
            )

            if (openAlertDialog.value) {
                MinimalDialog(
                    onDismissRequest = { openAlertDialog.value = false }
                )
            }
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
        composable(
            route = MainDestinations.SEARCH
        ) { _ ->
            SearchRoute()
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MinimalDialog(onDismissRequest: () -> Unit) {
    var magnitudeSliderPosition by remember { mutableStateOf(1f..9f) }
    var locationSliderPosition by remember { mutableFloatStateOf(50f) }
    var timeSliderPosition by remember { mutableStateOf(0f..30f) }

    val openMapSelectDialog = remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                ImageTitleValueLabel(
                    image = painterResource(R.drawable.ic_baseline_earthquake_24),
                    title = "Magnitude",
                    value = magnitudeSliderPosition.let {
                        when (it.start) {
                            1f -> "M<=1.0"
                            else -> "M${it.start.toBigDecimal().setScale(0, RoundingMode.HALF_EVEN)}.0"
                        } + " - " + when (it.endInclusive) {
                            9f -> "M>=9.0"
                            else -> "M${it.endInclusive.toBigDecimal().setScale(0, RoundingMode.HALF_EVEN)}.0"
                        }
                    }
                )
                RangeSlider(
                    value = magnitudeSliderPosition,
                    steps = 0,
                    onValueChange = { range -> magnitudeSliderPosition = range },
                    valueRange = 1f..9f,
                    onValueChangeFinished = {
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                ImageTitleValueLabel(
                    image = painterResource(R.drawable.ic_baseline_earthquake_24),
                    title = "Location",
                    value = "${
                        locationSliderPosition.toBigDecimal().setScale(0, RoundingMode.HALF_EVEN)
                    } km range within <location>"
                )
                Row {
                    Chip(
                        onClick = {},
                        modifier = Modifier.padding(horizontal = 16.dp).weight(1f)
                    ) {
                        Text(
                            text = "Current",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Chip(
                        onClick = { openMapSelectDialog.value = true },
                        modifier = Modifier.padding(horizontal = 16.dp).weight(1f)
                    ) {
                        Text(
                            text = "Select",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                Slider(
                    value = locationSliderPosition,
                    steps = 0,
                    onValueChange = { range -> locationSliderPosition = range },
                    valueRange = 1f..100f,
                    onValueChangeFinished = {
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                ImageTitleValueLabel(
                    image = painterResource(R.drawable.ic_baseline_earthquake_24),
                    title = "Time",
                    value = timeSliderPosition.let {
                        val start = when (it.start) {
                            0f -> "today"
                            else -> "${it.start.toBigDecimal().setScale(0, RoundingMode.HALF_EVEN)} days ago"
                        }
                        val end = when (it.endInclusive) {
                            0f -> "today"
                            else -> "${it.endInclusive.toBigDecimal().setScale(0, RoundingMode.HALF_EVEN)} days ago"
                        }

                        "From $start" + if (it.start != it.endInclusive) " until $end" else ""
                    }
                )
                Row {
                    Chip(
                        onClick = {},
                        modifier = Modifier.padding(horizontal = 16.dp).weight(1f)
                    ) {
                        Text(
                            text = "Recent",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Chip(
                        onClick = {},
                        modifier = Modifier.padding(horizontal = 16.dp).weight(1f)
                    ) {
                        Text(
                            text = "Range",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                RangeSlider(
                    value = timeSliderPosition,
                    steps = 0,
                    onValueChange = { range -> timeSliderPosition = range },
                    valueRange = 0f..30f,
                    onValueChangeFinished = {
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Row {
                    TextButton(
                        onClick = {},
                        modifier = Modifier.padding(horizontal = 16.dp).weight(1f)
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = {},
                        modifier = Modifier.padding(horizontal = 16.dp).weight(1f)
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
        if (openMapSelectDialog.value) {
            MapSelectDialog(
                onDismissRequest = { openMapSelectDialog.value = false }
            )
        }
    }
}


@Composable
fun MapSelectDialog(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Box {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        HomeMapView(context)
                    }
                )
            }
        }
    }
}
