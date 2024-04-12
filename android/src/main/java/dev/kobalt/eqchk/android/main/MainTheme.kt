package dev.kobalt.eqchk.android.main

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
        colors = if (!isSystemInDarkTheme()) lightColors(
            primary = MainColors.primaryLight,
            primaryVariant = MainColors.primaryLight,
            secondary = MainColors.primaryLight,
            secondaryVariant = MainColors.primaryLight,
            background = MainColors.white,
            surface = MainColors.primaryLight,
            error = MainColors.primaryLight,
            onPrimary = MainColors.white,
            onSecondary = MainColors.white,
            onBackground = MainColors.black,
            onSurface = MainColors.white,
            onError = MainColors.white
        ) else darkColors(
            primary = MainColors.primaryDark,
            primaryVariant = MainColors.primaryDark,
            secondary = MainColors.primaryDark,
            secondaryVariant = MainColors.primaryDark,
            background = MainColors.black,
            surface = MainColors.primaryDark,
            error = MainColors.primaryDark,
            onPrimary = MainColors.white,
            onSecondary = MainColors.white,
            onBackground = MainColors.white,
            onSurface = MainColors.white,
            onError = MainColors.white
        ),
        content = content
    )
}