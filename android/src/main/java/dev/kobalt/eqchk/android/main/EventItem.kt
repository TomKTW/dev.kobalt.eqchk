package dev.kobalt.eqchk.android.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kobalt.eqchk.android.R

@Composable
fun EventItem(
    magnitudeText: String,
    timeText: String,
    locationText: String
) {
    Row {
        Text(
            text = magnitudeText,
            fontSize = 32.sp,
            modifier = Modifier.fillMaxHeight().align(Alignment.CenterVertically).padding(4.dp)
        )
        Column(
            modifier = Modifier.fillMaxHeight().align(Alignment.CenterVertically).padding(4.dp),
        ) {
            ImageText(
                painter = painterResource(R.drawable.ic_baseline_access_time_24),
                text = timeText,
            )
            ImageText(
                painter = painterResource(R.drawable.ic_baseline_location_on_24),
                text = locationText,
            )
        }
    }
}