package dev.kobalt.eqchk.android.details

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import eqchk.android.generated.resources.Res
import eqchk.android.generated.resources.ic_baseline_arrow_back_24
import eqchk.android.generated.resources.ic_baseline_open_in_browser_24
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    navigateBack: () -> Unit,
    navigateOpen: (String) -> Unit
) {
    val viewState: DetailsViewState? by viewModel.viewState.collectAsState/*WithLifecycle*/(null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(
                        onClick = { navigateBack() }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_baseline_arrow_back_24),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewState?.event?.detailsUri?.let { navigateOpen(it) } }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_baseline_open_in_browser_24),
                            contentDescription = "Open in browser"
                        )
                    }
                }
            )
        },
        content = {
            DetailsContent(
                viewState = viewState,
                onRefresh = {},
                modifier = Modifier.padding(it)
            )
        }
    )
}