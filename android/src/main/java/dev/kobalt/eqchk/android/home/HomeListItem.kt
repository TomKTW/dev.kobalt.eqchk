package dev.kobalt.eqchk.android.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.kobalt.eqchk.android.main.EventItem

@Composable
fun HomeListItem(event: HomeEventEntity, onClick: (HomeEventEntity) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().clickable { onClick.invoke(event) }
    ) {
        EventItem(
            magnitudeText = event.magnitude,
            timeText = event.time,
            locationText = event.location
        )
        Divider(
            color = Color.Gray,
            modifier = Modifier.height(1.dp).fillMaxWidth()
        )
    }
}