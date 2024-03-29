package dev.kobalt.eqchk.android.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import dev.kobalt.eqchk.android.R
import dev.kobalt.eqchk.android.base.BaseFragment
import dev.kobalt.eqchk.android.component.LocationConverter
import dev.kobalt.eqchk.android.databinding.DetailsBinding
import dev.kobalt.eqchk.android.extension.ifLet
import dev.kobalt.eqchk.android.extension.toEventIntensity
import dev.kobalt.eqchk.android.extension.toSpannedHtml
import kotlinx.coroutines.launch
import java.math.RoundingMode

class DetailsFragment : BaseFragment<DetailsBinding>() {

    val viewModel: DetailsViewModel by viewModels()

    companion object {
        val uidKey = "uid"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.load(requireArguments().getString(uidKey)!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleScope.launchWhenCreated {
            viewModel.pageState.collect {
                viewBinding?.apply {
                    detailsContainer.isVisible = it == Page.Info
                    mapContainer.isVisible = it == Page.Map
                }
            }
        }
        viewLifecycleScope.launchWhenCreated {
            viewModel.dataState.collect { item ->
                viewBinding?.apply {
                    item?.let { mapMap.updateItem(it) }
                    detailsEventCard.apply(
                        item,
                        application.locationManager.locationPointFlow.replayCache.firstOrNull()
                    )
                    detailsCoordinatesLabel.text =
                        ifLet(item?.latitude, item?.longitude) { latitude, longitude ->
                            LocationConverter.locationToStringDMS(latitude, longitude, 1)
                        }
                    depthLabel.text =
                        item?.depth?.toBigDecimal()?.setScale(0, RoundingMode.HALF_EVEN)
                            ?.let { "$it ${getResourceString(R.string.kilometers)}" } ?: "-"
                    tectonicSummaryLabel.text = item?.tectonicSummary?.toSpannedHtml()
                    impactSummaryLabel.text = item?.impactSummary?.toSpannedHtml()
                    estimatedIntensityScale.value =
                        item?.estimatedIntensity?.toEventIntensity()
                    estimatedIntensityValueLabel.text =
                        item?.estimatedIntensity?.toEventIntensity()?.label
                            ?: getResourceString(R.string.not_available)
                    communityIntensityScale.value =
                        item?.communityIntensity?.toEventIntensity()
                    communityIntensityValueLabel.text =
                        item?.communityIntensity?.toEventIntensity()?.label
                            ?: getResourceString(R.string.not_available)
                }
            }
        }
        viewBinding?.apply {
            headerBackButton.setOnClickListener { backstack.goBack() }
            headerDetailsButton.setOnClickListener {
                viewModel.dataState.replayCache.firstOrNull()?.detailsUrl?.let {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                    startActivity(browserIntent)
                }
            }
            footerDetailsButton.setOnClickListener {
                viewModel.viewModelScope.launch { viewModel.pageState.emit(Page.Info) }
            }
            footerMapButton.setOnClickListener {
                viewModel.viewModelScope.launch { viewModel.pageState.emit(Page.Map) }
            }
        }
    }

    enum class Page {
        Info, Map
    }

}