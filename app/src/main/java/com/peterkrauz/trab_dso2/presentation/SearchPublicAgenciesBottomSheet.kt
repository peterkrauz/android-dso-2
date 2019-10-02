package com.peterkrauz.trab_dso2.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.peterkrauz.trab_dso2.R

class SearchPublicAgenciesBottomSheet : BottomSheetDialogFragment() {

    private val listener
        get() = context as SearchAgenciesListener

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
    }

    private fun setupView() {

    }

}