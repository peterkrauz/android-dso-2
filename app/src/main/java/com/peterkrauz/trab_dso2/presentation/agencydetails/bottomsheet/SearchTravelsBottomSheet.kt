package com.peterkrauz.trab_dso2.presentation.agencydetails.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.peterkrauz.trab_dso2.BuildConfig
import com.peterkrauz.trab_dso2.R
import com.peterkrauz.trab_dso2.presentation.agencydetails.AgencyDetailsViewModel
import com.peterkrauz.trab_dso2.presentation.common.bottomsheet.InputBottomSheet
import com.peterkrauz.trab_dso2.utils.extensions.text
import com.peterkrauz.trab_dso2.utils.extensions.textOrBlank
import kotlinx.android.synthetic.main.bottom_sheet_search_travels.*

class SearchTravelsBottomSheet : InputBottomSheet() {

    override val viewModel by lazy {
        ViewModelProviders.of(requireActivity())[AgencyDetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_search_travels, container, false)
    }

    override fun setupView() {
        buttonSearch.setOnClickListener {
            validateAndSearch()
            dismiss()
        }

        if (BuildConfig.DEBUG) {
            fillInTravelData()
        }
    }

    override fun setupObservers() {
        viewModel.travelsTextErrorLiveData.observe(this, ::setErrors)
        viewModel.validationCompleteLiveEvent.observe(this) { dismiss() }
    }

    override fun validateAndSearch() {
        val startDateFrom = textInputLayoutTravelStartDateFrom.textOrBlank()
        val startDateUntil = textInputLayoutTravelStartDateUntil.textOrBlank()
        val endDateFrom = textInputLayoutTravelEndDateFrom.textOrBlank()
        val endDateUntil = textInputLayoutTravelEndDateUntil.textOrBlank()

        viewModel.validateAndSearch(startDateFrom, startDateUntil, endDateFrom, endDateUntil)
    }

    private fun setErrors(errorBody: TravelFieldsErrorBody) = with(errorBody) {
        textInputLayoutTravelStartDateFrom.error = setError(startDateFromError)
        textInputLayoutTravelStartDateUntil.error = setError(startDateUntilError)
        textInputLayoutTravelEndDateFrom.error = setError(endDateFromError)
        textInputLayoutTravelEndDateUntil.error = setError(endDateUntilError)
    }

    private fun setError(errorType: TravelFieldErrorType): String? = when (errorType) {
        TravelFieldErrorType.NO_ERROR -> null
        TravelFieldErrorType.INVALID_RANGE -> getString(R.string.field_out_of_range)
        TravelFieldErrorType.BLANK_FIELD -> getString(R.string.this_field_is_demanded)
        TravelFieldErrorType.INVALID_FORMAT -> getString(R.string.field_in_wrong_format)
    }

    private fun fillInTravelData() {
        textInputLayoutTravelStartDateFrom.text = "01/01/2019"
        textInputLayoutTravelStartDateUntil.text = "30/01/2019"
        textInputLayoutTravelEndDateFrom.text = "01/01/2019"
        textInputLayoutTravelEndDateUntil.text = "30/01/2019"
    }
}