package dev.kobalt.eqchk.android.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.kobalt.eqchk.android.R
import dev.kobalt.eqchk.android.details.ImageTitleValueLabel
import java.math.RoundingMode

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterDialog(onDismissRequest: () -> Unit) {
    var magnitudeSliderPosition by remember { mutableStateOf(1f..9f) }
    var locationSliderPosition by remember { mutableFloatStateOf(50f) }
    var timeSliderPosition by remember { mutableStateOf(0f..30f) }

    val openMapSelectDialog = remember { mutableStateOf(false) }
    val openDateTimeRangeSelectDialog = remember { mutableStateOf(false) }

    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = { onDismissRequest() }) {
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
                        onClick = { openDateTimeRangeSelectDialog.value = true },
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
                        onClick = { onDismissRequest.invoke() },
                        modifier = Modifier.padding(horizontal = 16.dp).weight(1f)
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = { onDismissRequest.invoke() },
                        modifier = Modifier.padding(horizontal = 16.dp).weight(1f)
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
        if (openMapSelectDialog.value) {
            FilterMapSelectDialog(
                onDismissRequest = { openMapSelectDialog.value = false }
            )
        }
        if (openDateTimeRangeSelectDialog.value) {
            FilterDateTimeRangeSelectDialog(
                onDismissRequest = { openDateTimeRangeSelectDialog.value = false }
            )
        }
    }
}