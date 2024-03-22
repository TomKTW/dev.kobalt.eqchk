package dev.kobalt.earthquakecheck.android.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.slider.RangeSlider
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import dev.kobalt.earthquakecheck.android.base.BaseFragment
import dev.kobalt.earthquakecheck.android.databinding.SearchBinding
import dev.kobalt.earthquakecheck.android.details.DetailsKey
import dev.kobalt.earthquakecheck.android.extension.toString
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset


class SearchFragment : BaseFragment<SearchBinding>() {

    val viewModel: SearchViewModel by viewModels()

    val rangeListener = RangeSlider.OnChangeListener { slider, _, _ ->
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleScope.launchWhenCreated {
            viewModel.dataState.collect {
                viewBinding?.apply {
                    listRecycler.adapter.list = it
                }
            }
        }
        viewBinding?.apply {
            listRecycler.onItemSelect = {
                backstack.goTo(DetailsKey(it.id!!))
            }
            magnitudeInput.addOnChangeListener(rangeListener)
            magnitudeInput.valueFrom = 1f
            magnitudeInput.valueTo = 10f
            magnitudeInput.values = listOf(
                viewModel.formMinMagnitude?.toFloat() ?: magnitudeInput.valueFrom,
                viewModel.formMaxMagnitude?.toFloat() ?: magnitudeInput.valueTo
            )
            estimatedIntensityInput.addOnChangeListener(rangeListener)
            estimatedIntensityInput.valueFrom = 0f
            estimatedIntensityInput.valueTo = 12f
            estimatedIntensityInput.values = listOf(
                viewModel.formMinEstimatedIntensity?.toFloat() ?: estimatedIntensityInput.valueFrom,
                viewModel.formMaxEstimatedIntensity?.toFloat() ?: estimatedIntensityInput.valueTo
            )
            communityIntensityInput.addOnChangeListener(rangeListener)
            communityIntensityInput.valueFrom = 0f
            communityIntensityInput.valueTo = 12f
            communityIntensityInput.values = listOf(
                viewModel.formMinCommunityIntensity?.toFloat() ?: communityIntensityInput.valueFrom,
                viewModel.formMaxCommunityIntensity?.toFloat() ?: communityIntensityInput.valueTo
            )
            depthInput.addOnChangeListener(rangeListener)
            depthInput.valueFrom = -100f
            depthInput.valueTo = 1000f
            depthInput.values = listOf(
                viewModel.formMinMagnitude?.toFloat() ?: depthInput.valueFrom,
                viewModel.formMaxMagnitude?.toFloat() ?: depthInput.valueTo
            )
            timestampSetButton.setOnClickListener {
                MaterialDatePicker.Builder.dateRangePicker()
                    .setTitleText("Select dates")
                    .setSelection(
                        androidx.core.util.Pair(
                            MaterialDatePicker.thisMonthInUtcMilliseconds(),
                            MaterialDatePicker.todayInUtcMilliseconds()
                        )
                    )
                    .setCalendarConstraints(
                        CalendarConstraints.Builder()
                            .setValidator(DateValidatorPointBackward.now())
                            .setEnd(
                                LocalDateTime.now().atZone(ZoneId.ofOffset("UTC", ZoneOffset.UTC))
                                    .toInstant().toEpochMilli()
                            )
                            .build()
                    )
                    .build().also {
                        it.addOnPositiveButtonClickListener {
                            val timestampMin = LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(it.first),
                                ZoneId.of("UTC")
                            )
                            val timestampMax = LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(it.second),
                                ZoneId.of("UTC")
                            )
                            viewModel.formMinTimestamp = timestampMin
                            viewModel.formMaxTimestamp = timestampMax
                            timestampSetButton.text =
                                "${timestampMin.toString("yyyy-MM-dd")} - ${timestampMax.toString("yyyy-MM-dd")}"
                        }
                    }.show(childFragmentManager, "")
            }
            headerSearchButton.setOnClickListener {
                formContainer.isVisible = true
                listContainer.isVisible = false
            }
            submitButton.setOnClickListener {
                formContainer.isVisible = false
                listContainer.isVisible = true
                viewModel.load()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding?.apply {
            magnitudeInput.removeOnChangeListener(rangeListener)
            estimatedIntensityInput.removeOnChangeListener(rangeListener)
            communityIntensityInput.removeOnChangeListener(rangeListener)
            depthInput.removeOnChangeListener(rangeListener)
        }
    }

}