package com.peterkrauz.trab_dso2.presentation.traveldetails

import android.os.Bundle
import com.peterkrauz.trab_dso2.R
import com.peterkrauz.trab_dso2.data.entities.Travel
import com.peterkrauz.trab_dso2.presentation.common.base.BaseActivity
import com.peterkrauz.trab_dso2.utils.IntentExtras
import kotlinx.android.synthetic.main.activity_travel_details.*

class TravelDetailsActivity : BaseActivity() {

    private val travel: Travel by lazy { intent?.extras?.getParcelable(IntentExtras.EXTRA_TRAVEL) as Travel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_details)

        setupToolbar()
        setupView()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)

        supportActionBar?.run {
            setHomeAsUpIndicator(R.drawable.ic_back_white_24)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun setupView() {
        with (travel) {
            textViewPersonName.text = personName
            textViewTakeoff.text = startDate
            textViewLanding.text = endDate
            textViewTotalCost.text = totalCost.toString()
            textViewTravelReason.text = travelReason
        }
    }

}
