package dev.kobalt.eqchk.android.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeListContent(
    viewState: HomeViewState,
    onRefresh: () -> Unit,
    onEventClick: (HomeEventEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val pullToRefreshState = rememberPullRefreshState(
        refreshing = viewState.isLoading,
        onRefresh = { onRefresh.invoke() }
    )

    Box(
        modifier = Modifier
            .pullRefresh(pullToRefreshState),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(modifier.fillMaxWidth().fillMaxHeight()) {
            items(viewState.list) {
                HomeListItem(it, onEventClick)
            }
        }
        PullRefreshIndicator(
            viewState.isLoading,
            pullToRefreshState
        )
    }
}