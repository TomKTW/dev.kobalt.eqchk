package dev.kobalt.eqchk.main

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Composable
fun MainTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (!isSystemInDarkTheme()) lightColors() else darkColors(),
        content = content
    )
}