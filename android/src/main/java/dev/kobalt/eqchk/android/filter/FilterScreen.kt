package dev.kobalt.eqchk.android.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import dev.kobalt.eqchk.android.R
import dev.kobalt.eqchk.android.details.ImageTitleValueLabel

@Composable
fun FilterScreen(
    navigateBack: () -> Unit,
) {
    val showFilterMagnitudeDialog = remember { mutableStateOf(false) }
    val showFilterLocationDialog = remember { mutableStateOf(false) }
    val showFilterDateTimeDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Filter") },
                navigationIcon = {
                    IconButton(
                        onClick = { navigateBack() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { navigateBack() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_south_24),
                            contentDescription = "Save"
                        )
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()).padding(it)
            ) {
                ImageTitleValueLabel(
                    image = painterResource(R.drawable.ic_baseline_earthquake_24),
                    title = "Magnitude",
                    value = "Between M5.0 and M6.0",
                    onClick = { showFilterMagnitudeDialog.value = true }
                )
                ImageTitleValueLabel(
                    image = painterResource(R.drawable.ic_baseline_location_on_24),
                    title = "Location",
                    value = "## km range within <location>",
                    onClick = { showFilterLocationDialog.value = true }
                )
                ImageTitleValueLabel(
                    image = painterResource(R.drawable.ic_baseline_access_time_24),
                    title = "Date & Time",
                    value = "Between 30 days ago and today",
                    onClick = { showFilterDateTimeDialog.value = true }
                )
            }
        }
    )
    if (showFilterMagnitudeDialog.value) FilterMagnitudeDialog(
        onDismissRequest = { showFilterMagnitudeDialog.value = false }
    )
    if (showFilterLocationDialog.value) FilterLocationDialog(
        onDismissRequest = { showFilterLocationDialog.value = false }
    )
    if (showFilterDateTimeDialog.value) FilterDateTimeDialog(
        onDismissRequest = { showFilterDateTimeDialog.value = false }
    )
}