package dev.kobalt.eqchk.android.filter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.math.BigDecimal
import java.math.RoundingMode

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterMagnitudeDialog(
    currentMagnitudeRange: ClosedRange<BigDecimal>,
    onDismissRequest: () -> Unit,
    onSubmit: (ClosedRange<BigDecimal>) -> Unit
) {
    var sliderPosition by remember {
        mutableStateOf(
            currentMagnitudeRange.start.multiply(10.toBigDecimal())
                .toFloat()..currentMagnitudeRange.endInclusive.multiply(10.toBigDecimal()).toFloat()
        )
    }
    val magnitudeMin = sliderPosition.start.toBigDecimal().divide(10.toBigDecimal(), RoundingMode.HALF_EVEN)
        .setScale(1, RoundingMode.HALF_EVEN)
    val magnitudeMax = sliderPosition.endInclusive.toBigDecimal().divide(10.toBigDecimal(), RoundingMode.HALF_EVEN)
        .setScale(1, RoundingMode.HALF_EVEN)

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
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Magnitude",
                    fontSize = 20.sp
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                Row {
                    Text(
                        text = when (magnitudeMin) {
                            BigDecimal("0.1") -> "M0.1>"
                            else -> "M${magnitudeMin}"
                        },
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = when (magnitudeMax) {
                            BigDecimal("10.0") -> ">M10.0"
                            else -> "M${magnitudeMax}"
                        },
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(1f)
                    )
                }
                RangeSlider(
                    value = sliderPosition,
                    steps = 100,
                    onValueChange = { range -> sliderPosition = range },
                    valueRange = 1f..100f,
                    onValueChangeFinished = { }
                )
                Row {
                    TextButton(
                        onClick = { onDismissRequest.invoke() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = { onSubmit.invoke(magnitudeMin..magnitudeMax); onDismissRequest.invoke() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}