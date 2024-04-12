package dev.kobalt.eqchk.android.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kobalt.eqchk.android.main.MainColors

@Composable
fun HomeListItem(event: HomeEventEntity, onClick: (HomeEventEntity) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick.invoke(event) }.padding(12.dp)
    ) {
        Text(
            text = event.magnitude,
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(0.2f).fillMaxHeight().align(Alignment.CenterVertically)
        )
        Column(
            modifier = Modifier.weight(0.8f).padding(vertical = 6.dp)
        ) {
            Text(
                text = event.location,
                fontSize = 20.sp
            )
            Text(
                text = event.time + " ago",
                fontSize = 14.sp,
                color = MainColors.black.copy(alpha = 0.5f)
            )
        }
    }
    Divider(
        color = Color.Black.copy(alpha = 0.1f),
        modifier = Modifier.height(1.dp).fillMaxWidth()
    )
}