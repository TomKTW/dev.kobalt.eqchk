package dev.kobalt.eqchk.android.filter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDateTimeDialog(
    currentDateTimeRange: Pair<Instant?, Instant?>,
    onDismiss: () -> Unit,
    onClear: () -> Unit,
    onSubmit: (Pair<Instant?, Instant?>) -> Unit
) {
    val dateRangeState: DateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = currentDateTimeRange.first?.toEpochMilli(),
        initialSelectedEndDateMillis = currentDateTimeRange.second?.toEpochMilli()
    )
    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Date & Time",
                    fontSize = 20.sp
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                DateRangePicker(
                    title = null,
                    showModeToggle = false,
                    state = dateRangeState,
                    modifier = Modifier.weight(1f)
                )
                Row {
                    TextButton(
                        onClick = { onDismiss.invoke() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = { onClear.invoke(); onDismiss.invoke() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Clear")
                    }
                    TextButton(
                        onClick = {
                            onSubmit.invoke(
                                dateRangeState.selectedStartDateMillis?.let { Instant.ofEpochMilli(it) } to dateRangeState.selectedEndDateMillis?.let {
                                    Instant.ofEpochMilli(it)
                                }); onDismiss.invoke()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}