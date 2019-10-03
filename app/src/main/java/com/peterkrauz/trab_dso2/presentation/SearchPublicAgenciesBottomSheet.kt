package com.peterkrauz.trab_dso2.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.peterkrauz.trab_dso2.R
import kotlinx.android.synthetic.main.bottom_sheet_search_public_agencies.*

class SearchPublicAgenciesBottomSheet : BottomSheetDialogFragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(requireActivity())[PublicAgenciesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_search_public_agencies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupObservers()
    }

    private fun setupView() {
        buttonSearch.setOnClickListener {
            validateAndSearch()
        }
    }

    private fun validateAndSearch() {
        val inputText = textInputLayoutAgenciesDescription.editText?.text
        if (inputText.isNullOrBlank()) {
            viewModel.onSearchFieldError()
            return
        }

        viewModel.searchAgencies(inputText.toString())
        dismiss()
    }

    private fun setupObservers() {
        viewModel.publicAgencyTextErrorLiveData.observe(this, ::setError)
    }

    private fun setError(error: Boolean) {
        textInputLayoutAgenciesDescription.error = if (error) {
            getString(R.string.this_field_is_demanded)
        } else {
            null
        }
    }

}