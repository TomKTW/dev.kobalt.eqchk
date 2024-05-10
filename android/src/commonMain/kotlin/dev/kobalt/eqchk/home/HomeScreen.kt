package dev.kobalt.eqchk.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import eqchk.android.generated.resources.Res
import eqchk.android.generated.resources.ic_baseline_filter_list_24
import eqchk.android.generated.resources.ic_baseline_list_alt_24
import eqchk.android.generated.resources.ic_baseline_map_24
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToDetails: (String) -> Unit,
    navigateToFilter: () -> Unit
) {
    val viewState: HomeViewState by viewModel.viewState.collectAsState/*WithLifecycle*/(
        HomeViewState(
            false,
            HomePage.Events,
            emptyList(),
            EventFilter()
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        when (viewState.page) {
                            HomePage.Events -> "Events"
                            HomePage.Map -> "Map"
                        }
                    )
                },
                actions = {
                    IconButton(
                        onClick = { navigateToFilter.invoke() }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_baseline_filter_list_24),
                            contentDescription = "Filter"
                        )
                    }
                }
            )
        },
        content = {
            when (viewState.page) {
                HomePage.Events -> HomeListContent(
                    viewState = viewState,
                    onRefresh = {
                        viewModel.refresh()
                    },
                    onEventClick = {
                        navigateToDetails.invoke(it.id)
                    },
                    modifier = Modifier.padding(it)
                )

                HomePage.Map -> Box/*HomeMapContent*/(
                    modifier = Modifier.padding(it)
                )
            }
        },
        bottomBar = {
            BottomAppBar {
                IconButton(
                    onClick = { viewModel.updatePage(HomePage.Events) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_baseline_list_alt_24),
                        contentDescription = "List"
                    )
                }
                IconButton(
                    onClick = { viewModel.updatePage(HomePage.Map) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_baseline_map_24),
                        contentDescription = "Map"
                    )
                }
            }
        }
    )
}