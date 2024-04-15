package dev.kobalt.eqchk.android.filter

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.RangeSlider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterMagnitudeDialog(onDismissRequest: () -> Unit) {
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
            RangeSlider(
                value = 0f..9f,
                steps = 0,
                onValueChange = { range -> },
                valueRange = 0f..30f,
                onValueChangeFinished = {
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}