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
    navigateToSearch: () -> Unit
) {
    val viewState: HomeViewState by viewModel.viewState.collectAsStateWithLifecycle(
        HomeViewState(
            false,
            HomePage.List,
            emptyList()
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(viewState.page.titleResId)) },
                actions = {
                    IconButton(
                        onClick = { navigateToSearch.invoke() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_search_24),
                            contentDescription = "Search"
                        )
                    }
                }
            )
        },
        content = {
            when (viewState.page) {
                HomePage.Map -> HomeMapContent(
                    modifier = Modifier.padding(it)
                )

                HomePage.List -> HomeListContent(
                    viewState = viewState,
                    onRefresh = {
                        viewModel.refresh()
                    },
                    onEventClick = {
                        navigateToDetails.invoke(it.id)
                    },
                    modifier = Modifier.padding(it)
                )

                HomePage.Options -> HomeOptionsContent(
                    modifier = Modifier.padding(it)
                )
            }
        },
        bottomBar = {
            BottomAppBar {
                IconButton(
                    onClick = { viewModel.updatePage(HomePage.Map) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_baseline_map_24),
                        contentDescription = "Map"
                    )
                }
                IconButton(
                    onClick = { viewModel.updatePage(HomePage.List) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_baseline_list_alt_24),
                        contentDescription = "List"
                    )
                }
                IconButton(
                    onClick = { viewModel.updatePage(HomePage.Options) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_baseline_settings_24),
                        contentDescription = "Settings"
                    )
                }
            }
        }
    )
}