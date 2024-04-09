package dev.kobalt.eqchk.android.search

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.kobalt.eqchk.android.base.BaseFragment

@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    val viewModel: SearchViewModel by viewModels()

    /*val rangeListener = RangeSlider.OnChangeListener { slider, _, _ ->
        viewBinding?.apply {
            val startValue = slider.values.getOrNull(0).let {
                if (it == slider.valueFrom) "<$it" else if (it == slider.valueTo) "$it>" else it.toString()
            }
            val endValue = slider.values.getOrNull(1).let {
                if (it == slider.valueFrom) "<$it" else if (it == slider.valueTo) "$it>" else it.toString()
            }
            val stringValue = if (startValue == endValue) startValue else "$startValue - $endValue"
            val minValue = slider.values.getOrNull(0)?.takeIf { it != slider.valueFrom }?.toDouble()
            val maxValue = slider.values.getOrNull(1)?.takeIf { it != slider.valueTo }?.toDouble()
            when (slider) {
                viewBinding?.magnitudeInput -> {
                    magnitudeValueLabel.text = stringValue
                    viewModel.formMinMagnitude = minValue
                    viewModel.formMaxMagnitude = maxValue
                }
                viewBinding?.estimatedIntensityInput -> {
                    estimatedIntensityValueLabel.text = stringValue
                    viewModel.formMinEstimatedIntensity = minValue
                    viewModel.formMaxEstimatedIntensity = maxValue
                }
                viewBinding?.communityIntensityInput -> {
                    communityIntensityValueLabel.text = stringValue
                    viewModel.formMinCommunityIntensity = minValue
                    viewModel.formMaxCommunityIntensity = maxValue
                }
                viewBinding?.depthInput -> {
                    depthValueLabel.text = stringValue
                    viewModel.formMinDepth = minValue
                    viewModel.formMaxDepth = maxValue
                }
            }
        }
    }*/

    /*
    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding?.apply {
            magnitudeInput.removeOnChangeListener(rangeListener)
            estimatedIntensityInput.removeOnChangeListener(rangeListener)
            communityIntensityInput.removeOnChangeListener(rangeListener)
            depthInput.removeOnChangeListener(rangeListener)
        }
    }*/

    private fun navigateToDetails(id: String) {
        findNavController().navigate(SearchFragmentDirections.actionSearchToDetails(id))
    }

}