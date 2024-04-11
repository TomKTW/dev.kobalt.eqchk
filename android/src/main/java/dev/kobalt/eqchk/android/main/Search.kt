package dev.kobalt.eqchk.android.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import dev.kobalt.eqchk.android.search.SearchViewModel

@Composable
fun SearchRoute(
    viewModel: SearchViewModel = hiltViewModel()
) {
    //val state = viewModel.viewState.collectAsStateWithLifecycle(null)
}