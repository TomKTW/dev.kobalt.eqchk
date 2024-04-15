package dev.kobalt.eqchk.android.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.kobalt.eqchk.android.R

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetails: (String) -> Unit,
    navigateToFilter: () -> Unit
) {
    val viewState: HomeViewState by viewModel.viewState.collectAsStateWithLifecycle(
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
                title = { Text(stringResource(viewState.page.titleResId)) },
                actions = {
                    IconButton(
                        onClick = { navigateToFilter.invoke() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_filter_list_24),
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

                HomePage.Map -> HomeMapContent(
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
                        painter = painterResource(R.drawable.ic_baseline_list_alt_24),
                        contentDescription = "List"
                    )
                }
                IconButton(
                    onClick = { viewModel.updatePage(HomePage.Map) },
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