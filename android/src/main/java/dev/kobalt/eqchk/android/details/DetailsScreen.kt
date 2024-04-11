package dev.kobalt.eqchk.android.details

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.kobalt.eqchk.android.R

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateOpen: (Uri) -> Unit
) {
    val viewState: DetailsViewState? by viewModel.viewState.collectAsStateWithLifecycle(null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(
                        onClick = { navigateBack() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewState?.entity?.detailsUri?.let { navigateOpen(it) } }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_open_in_browser_24),
                            contentDescription = "Open in browser"
                        )
                    }
                }
            )
        },
        content = {
            DetailsContent(
                event = viewState?.entity,
                modifier = Modifier.padding(it)
            )
        },
        bottomBar = {
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
    )
}