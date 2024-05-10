package dev.kobalt.eqchk.android.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import dev.kobalt.eqchk.android.details.ImageTitleValueLabel
import eqchk.android.generated.resources.*
import kotlinx.datetime.Instant
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun FilterScreen(
    navigateBack: () -> Unit,
) {
    val locationRange = remember { mutableIntStateOf(1) }
    val locationCoordinates = remember { mutableStateOf<Pair<Double, Double>?>(null) }

    val magnitudeRange = remember { mutableStateOf(BigDecimal.parseString("0.1")..BigDecimal.parseString("10.0")) }

    val dateTimeRange = remember { mutableStateOf<Pair<Instant?, Instant?>>(null to null) }

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
                            painter = painterResource(Res.drawable.ic_baseline_arrow_back_24),
                            contentDescription = "Back"
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
                    image = painterResource(Res.drawable.ic_baseline_earthquake_24),
                    title = "Magnitude",
                    value = when (magnitudeRange.value.start) {
                        BigDecimal.parseString("0.1") -> when (magnitudeRange.value.endInclusive) {
                            BigDecimal.parseString("10.0") -> "Any"
                            else -> "Below M${magnitudeRange.value.endInclusive}"
                        }

                        else -> when (magnitudeRange.value.endInclusive) {
                            BigDecimal.parseString("10.0") -> "Above M${magnitudeRange.value.start}"
                            else -> "Between M${magnitudeRange.value.start} and M${magnitudeRange.value.endInclusive}"
                        }
                    },
                    onClick = { showFilterMagnitudeDialog.value = true }
                )
                ImageTitleValueLabel(
                    image = painterResource(Res.drawable.ic_baseline_location_on_24),
                    title = "Location",
                    value = if (locationCoordinates.value == null) {
                        "Any"
                    } else {
                        "${locationRange.intValue} km range within ${
                            locationCoordinates.value!!.first.toBigDecimal().scale(2)
                        }, ${locationCoordinates.value!!.second.toBigDecimal().scale(2)}"
                    },
                    onClick = { showFilterLocationDialog.value = true }
                )
                ImageTitleValueLabel(
                    image = painterResource(Res.drawable.ic_baseline_access_time_24),
                    title = "Date & Time",
                    value = dateTimeRange.value.first?.let { startDateTime ->
                        dateTimeRange.value.second?.let { endDateTime ->
                            "Between ${
                                startDateTime.toString() /*.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE) */
                            } and ${
                                endDateTime.toString() /*.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE) */
                            }"
                        } ?: run {
                            "After ${
                                startDateTime.toString() /*.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE) */
                            }"
                        }
                    } ?: dateTimeRange.value.second?.let { endDateTime ->
                        "Before ${endDateTime.toString() /*.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE) */}"
                    } ?: run {
                        "Most recent"
                    },
                    onClick = { showFilterDateTimeDialog.value = true }
                )
            }
        }
    )
    if (showFilterMagnitudeDialog.value) FilterMagnitudeDialog(
        currentMagnitudeRange = magnitudeRange.value,
        onDismiss = { showFilterMagnitudeDialog.value = false },
        onSubmit = { magnitudeRange.value = it }
    )
    if (showFilterLocationDialog.value) FilterLocationDialog(
        currentRange = locationRange.intValue,
        currentCoordinates = locationCoordinates.value ?: (0.0 to 0.0),
        onSubmit = { range, coordinates -> locationRange.intValue = range; locationCoordinates.value = coordinates },
        onClear = { locationRange.intValue = 1; locationCoordinates.value = null },
        onDismiss = { showFilterLocationDialog.value = false }
    )
    if (showFilterDateTimeDialog.value) FilterDateTimeDialog(
        currentDateTimeRange = dateTimeRange.value,
        onDismiss = { showFilterDateTimeDialog.value = false },
        onClear = { dateTimeRange.value = null to null },
        onSubmit = { dateTimeRange.value = it }
    )
}