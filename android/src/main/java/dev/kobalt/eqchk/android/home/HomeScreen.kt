package dev.kobalt.eqchk.android.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import dev.kobalt.eqchk.android.R
import dev.kobalt.eqchk.android.event.EventEntity

@Composable
fun EventItem(event: EventEntity, onClick: (EventEntity) -> Unit) {
    Column(
        modifier = Modifier.clickable { onClick.invoke(event) }
    ) {
        Text(
            text = event.magnitude?.toString().orEmpty(),
            fontSize = 24.sp
        )
        Text(
            text = event.timestamp?.toString().orEmpty(),
            fontSize = 16.sp
        )
        Text(
            text = event.location.orEmpty(),
            fontSize = 16.sp
        )
    }
}

@Composable
fun HomeHeaderToolbar(
) {
    TopAppBar(
        title = { Text("Home") },
        actions = {
            IconButton(
                onClick = { }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_search_24),
                    contentDescription = "Search"
                )
            }
        }
    )
}

@Composable
fun HomeFooterToolbar() {
    BottomAppBar {
        IconButton(
            onClick = { },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_map_24),
                contentDescription = "Map"
            )
        }
        IconButton(
            onClick = { },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_list_alt_24),
                contentDescription = "List"
            )
        }
        IconButton(
            onClick = { },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_settings_24),
                contentDescription = "Settings"
            )
        }
    }
}

@Composable
fun HomeContent(
    viewState: HomeViewState?,
    onClick: (EventEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        items(viewState?.list.orEmpty()) {
            EventItem(it, onClick)
        }
    }
}

@Composable
fun HomeScreen(
    viewState: HomeViewState?,
    onClick: (EventEntity) -> Unit,
) {
    Scaffold(
        topBar = { HomeHeaderToolbar() },
        content = { HomeContent(viewState, onClick, Modifier.padding(it)) },
        bottomBar = { HomeFooterToolbar() }
    )
}


@Preview
@Composable
private fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(
            null,
            {}
        )
    }
}
