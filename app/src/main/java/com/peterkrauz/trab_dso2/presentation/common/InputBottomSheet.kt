package com.peterkrauz.trab_dso2.presentation.common

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class InputBottomSheet : BottomSheetDialogFragment() {

    abstract val viewModel: ViewModel

    abstract fun setupView()
    abstract fun setupObservers()

    abstract fun validateAndSearch()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupObservers()
    }

}