package dev.kobalt.eqchk.android.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import dev.kobalt.eqchk.android.R

@Composable
fun DetailsHeaderToolbar(
    onBackButtonClick: () -> Unit,
    onOpenButtonClick: () -> Unit
) {
    TopAppBar(
        title = { Text("Details") },
        navigationIcon = {
            IconButton(
                onClick = { onBackButtonClick() }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(
                onClick = { onOpenButtonClick() }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_open_in_browser_24),
                    contentDescription = "Open in browser"
                )
            }
        }
    )
}

@Composable
fun DetailsFooterToolbar() {
    BottomAppBar {
        IconButton(
            onClick = { },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_list_alt_24),
                contentDescription = "Info"
            )
        }
        IconButton(
            onClick = { },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_map_24),
                contentDescription = "Map"
            )
        }
    }
}

@Composable
fun DetailsContent(
    viewState: DetailsViewState?,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = viewState?.magnitude.orEmpty(),
            fontSize = 24.sp
        )
        Text(
            text = viewState?.time.orEmpty(),
            fontSize = 16.sp
        )
        Text(
            text = viewState?.location.orEmpty(),
            fontSize = 16.sp
        )
        Text(
            text = viewState?.depth.orEmpty(),
            fontSize = 16.sp
        )
        Text(
            text = viewState?.coordinates.orEmpty(),
            fontSize = 16.sp
        )
        Text(
            text = viewState?.tectonicSummary.toString(),
            fontSize = 16.sp
        )
        Text(
            text = viewState?.impactSummary.toString(),
            fontSize = 16.sp
        )
        Text(
            text = viewState?.estimatedIntensityValue.orEmpty(),
            fontSize = 16.sp
        )
        Text(
            text = viewState?.communityIntensityValue.orEmpty(),
            fontSize = 16.sp
        )
    }
}

@Composable
fun DetailsScreen(
    onBackButtonClick: () -> Unit,
    onOpenButtonClick: () -> Unit,
    viewState: DetailsViewState?
) {
    Scaffold(
        topBar = { DetailsHeaderToolbar(onBackButtonClick, onOpenButtonClick) },
        content = { DetailsContent(viewState, Modifier.padding(it)) },
        bottomBar = { DetailsFooterToolbar() }
    )
}


@Preview
@Composable
private fun DetailsScreenPreview() {
    MaterialTheme {
        DetailsScreen(
            {},
            {},
            null
        )
    }
}