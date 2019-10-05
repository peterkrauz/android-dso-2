package com.peterkrauz.trab_dso2.presentation.agencydetails

import android.os.Bundle
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.peterkrauz.trab_dso2.R
import com.peterkrauz.trab_dso2.data.entities.PublicAgency
import com.peterkrauz.trab_dso2.data.entities.Travel
import com.peterkrauz.trab_dso2.presentation.agencydetails.bottomsheet.SearchTravelsBottomSheet
import com.peterkrauz.trab_dso2.presentation.common.BaseActivity
import com.peterkrauz.trab_dso2.utils.IntentExtras
import com.peterkrauz.trab_dso2.utils.lazyViewModel
import kotlinx.android.synthetic.main.activity_agency_details.*

class AgencyDetailsActivity : BaseActivity() {

    private var searchTravelsBottomSheet: SearchTravelsBottomSheet? = null

    private val travelsAdapter by lazy {
        TravelsAdapter()
    }

    private val viewModel by lazyViewModel {
        val agency = intent.extras?.getParcelable<PublicAgency>(IntentExtras.EXTRA_AGENCY)!!
        AgencyDetailsViewModel(agency)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agency_details)

        setupToolbar()
        setupView()
        setupObservers()
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
        recyclerViewTravels.apply {
            adapter = travelsAdapter
            layoutManager = LinearLayoutManager(this@AgencyDetailsActivity)
            setHasFixedSize(true)
             addOnScrollListener(object: RecyclerView.OnScrollListener() {
                 override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                     super.onScrollStateChanged(recyclerView, newState)

                     if (!canScrollVertically(1)) {
                         viewModel.paginate()
                     }
                 }
             })
        }
        fabInsertTravelDates.setOnClickListener {
            viewModel.onSearchTravels()
        }
    }

    private fun setupObservers() {
        viewModel.travelsLiveData.observe(this, ::setTravels)
        viewModel.searchTravelsLiveEvent.observe(this) { onSearchTravels() }
    }

    private fun onSearchTravels() {
        searchTravelsBottomSheet =
            SearchTravelsBottomSheet()
        searchTravelsBottomSheet?.show(supportFragmentManager, "SearchTravelsBottomSheet")
    }

    private fun setTravels(travels: List<Travel>) {
        travelsAdapter.submitItems(travels.toMutableList())
    }
}
