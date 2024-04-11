package dev.kobalt.eqchk.android.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ImageText(
    painter: Painter,
    text: String,
    contentDescription: String? = null
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.size(16.dp).align(Alignment.CenterVertically)
        )
        Text(
            text = text,
            fontSize = 16.sp
        )
    }
}