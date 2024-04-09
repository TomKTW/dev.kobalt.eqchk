package dev.kobalt.eqchk.android.home

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.kobalt.eqchk.android.R
import dev.kobalt.eqchk.android.base.BaseFragment

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModels()

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            viewModel.toggleLocation()
            showToast(getResourceString(R.string.home_location_permission_granted))
        } else {
            showToast(getResourceString(R.string.home_location_permission_denied))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.load()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView?.setContent {
            MaterialTheme {
                val viewState: HomeViewState? by viewModel.viewState.collectAsState(null)
                Surface {
                    HomeScreen(
                        viewState,
                        onClick = {
                            navigateToDetails(it.id)
                        }
                    )
                }
            }
        }
    }

    /*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleScope.launchWhenCreated {
            viewModel.pageState.collect {
                viewBinding?.apply {
                    headerTitleLabel.text = when (it) {
                        Page.Map -> getResourceString(R.string.home_map)
                        Page.List -> getResourceString(R.string.home_list)
                        Page.Options -> getResourceString(R.string.home_options)
                    }
                    mapToggleButton.isVisible = it == Page.Map
                    mapContainer.root.isVisible = it == Page.Map
                    listContainer.root.isVisible = it == Page.List
                    settingsContainer.root.isVisible = it == Page.Options
                }
            }
        }
        viewLifecycleScope.launchWhenCreated {
            viewModel.dataState.collect {
                viewBinding?.apply {
                    mapContainer.mapMap.updateItems(it)
                    listContainer.listRecycler.adapter.list = it
                }
            }
        }
        viewLifecycleScope.launchWhenCreated {
            viewModel.loadState.collect {
                viewBinding?.apply {
                    listContainer.root.isRefreshing = it is HomeLoadViewState.Loading
                }
            }
        }
        viewLifecycleScope.launchWhenCreated {
            viewModel.locationPointFlow.collect {
                viewBinding?.apply {
                    mapContainer.mapMap.location = it
                    listContainer.listRecycler.adapter.point = it
                    mapContainer.mapDetailsContainer.apply {
                        apply(viewModel.mapSelectionFlow.replayCache.firstOrNull(), it)
                    }
                }
            }
        }
        viewLifecycleScope.launchWhenCreated {
            viewModel.mapOptionFlow.collect {
                viewBinding?.apply {
                    mapContainer.mapMap.type = it
                }
            }
        }
        viewLifecycleScope.launchWhenCreated {
            viewModel.mapSelectionFlow.collect { item ->
                viewBinding?.apply {
                    mapContainer.apply {
                        if (item?.latitude != null && item.longitude != null) {
                            mapMap.controller.animateTo(
                                GeoPoint(item.latitude, item.longitude)
                            )
                        }
                        mapDetailsContainer.apply {
                            startAnimation(SlideAnimation(this, 250, dp(128), item != null))
                            if (item != null) {
                                apply(item, viewModel.locationPointFlow.replayCache.firstOrNull())
                            }
                        }
                    }
                }
            }
        }
        viewBinding?.apply {
            headerSearchButton.setOnClickListener {
                navigateToSearch()
            }
            mapContainer.apply {
                mapMap.apply {
                    onTap = {
                        viewModel.viewModelScope.launch { viewModel.mapSelectionFlow.emit(null) }
                    }
                    onItemTap = {
                        val item = it.data
                        viewModel.viewModelScope.launch { viewModel.mapSelectionFlow.emit(item) }; true
                    }
                }
                mapDetailsContainer.apply {
                    translationY = dp(128).toFloat()
                    setOnClickListener {
                        viewModel.mapSelectionFlow.replayCache.firstOrNull()?.id?.let {
                            findNavController().navigate(HomeFragmentDirections.actionHomeToDetails(it))
                        }
                    }
                }
            }
            listContainer.apply {
                root.setOnRefreshListener {
                    viewModel.load(forceReload = true)
                }
                listRecycler.onItemSelect =
                    { item -> item.id?.let { navigateToDetails(it) } }
            }
            settingsContainer.apply {
                requestPermissionsButton.apply {
                    titleLabel.text = getResourceString(R.string.options_location_title)
                    subtitleLabel.text = getResourceString(R.string.options_location_subtitle)
                    optionSwitch.isChecked = viewModel.locationPositionEnabled == true
                    setOnClickListener {
                        if (arePermissionsGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
                            viewModel.toggleLocation()
                            optionSwitch.isChecked =
                                viewModel.locationPositionEnabled == true
                        } else {
                            locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    }
                }
                toggleNotificationButton.apply {
                    titleLabel.text = getResourceString(R.string.options_latest_title)
                    subtitleLabel.text = getResourceString(R.string.options_latest_subtitle)
                    optionSwitch.isChecked = viewModel.latestLoadEnabled == true
                    setOnClickListener {
                        viewModel.toggleNotifications()
                        optionSwitch.isChecked = viewModel.latestLoadEnabled == true
                    }
                }
            }
            mapToggleButton.setOnClickListener {
                HomeFilterPopupWindow(requireContext()).apply {
                    anchorView = viewBinding?.headerStack
                    onItemClick = { position ->
                        viewModel.viewModelScope.launch {
                            viewModel.mapOptionFlow.emit(
                                when (position) {
                                    1 -> HomeMapView.LegendType.Depth
                                    2 -> HomeMapView.LegendType.Timestamp
                                    3 -> HomeMapView.LegendType.EstimatedIntensity
                                    4 -> HomeMapView.LegendType.CommunityIntensity
                                    else -> HomeMapView.LegendType.None
                                }
                            )
                        }
                    }
                }.show()
            }
            footerMapButton.apply {
                background = RippleDrawable(
                    getResourceColorState(R.color.black),
                    null,
                    ShapeDrawable(RectShape())
                )
                setOnClickListener {
                    viewModel.viewModelScope.launch { viewModel.pageState.emit(Page.Map) }
                }
            }
            footerListButton.apply {
                background = RippleDrawable(
                    getResourceColorState(R.color.black),
                    null,
                    ShapeDrawable(RectShape())
                )
                setOnClickListener {
                    viewModel.viewModelScope.launch { viewModel.pageState.emit(Page.List) }
                }
            }
            footerOptionsButton.apply {
                background = RippleDrawable(
                    getResourceColorState(R.color.black),
                    null,
                    ShapeDrawable(RectShape())
                )
                setOnClickListener {
                    viewModel.viewModelScope.launch { viewModel.pageState.emit(Page.Options) }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewBinding?.mapContainer?.mapMap?.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewBinding?.mapContainer?.mapMap?.onPause()
    }*/

    private fun navigateToSearch() {
        findNavController().navigate(HomeFragmentDirections.actionHomeToSearch())
    }

    private fun navigateToDetails(id: String) {
        findNavController().navigate(HomeFragmentDirections.actionHomeToDetails(id))
    }

    enum class Page {
        Map, List, Options
    }

}