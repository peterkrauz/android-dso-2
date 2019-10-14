package com.peterkrauz.trab_dso2.presentation.agencydetails

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.peterkrauz.trab_dso2.R
import com.peterkrauz.trab_dso2.data.entities.PublicAgency
import com.peterkrauz.trab_dso2.data.entities.Travel
import com.peterkrauz.trab_dso2.presentation.agencydetails.bottomsheet.SearchTravelsBottomSheet
import com.peterkrauz.trab_dso2.presentation.common.paging.PaginatingActivity
import com.peterkrauz.trab_dso2.presentation.traveldetails.TravelDetailsActivity
import com.peterkrauz.trab_dso2.utils.IntentExtras
import com.peterkrauz.trab_dso2.utils.lazyViewModel
import kotlinx.android.synthetic.main.activity_agency_details.*

class AgencyDetailsActivity : PaginatingActivity<Travel>() {

    private val agency: PublicAgency by lazy { intent?.extras?.getParcelable(IntentExtras.EXTRA_AGENCY) as PublicAgency }

    override val adapter by lazy {
        TravelsAdapter {
            viewModel.onTravelClick(it)
        }
    }

    override val viewModel by lazyViewModel {
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
            adapter = this@AgencyDetailsActivity.adapter
            layoutManager = LinearLayoutManager(this@AgencyDetailsActivity)
            setHasFixedSize(true)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

    override fun setupObservers() {
        super.setupObservers()
        viewModel.travelsLiveData.observe(this, ::setTravels)
        viewModel.loadingLiveData.observe(this, ::setLoading)
        viewModel.searchTravelsLiveEvent.observe(this) { onSearchTravels() }
        viewModel.travelExpensesSumLiveData.observe(this, ::setExpensesSum)
        viewModel.travelClickedLiveEvent.observe(this, ::navigateToTravelDetails)
    }

    private fun setTravels(travels: List<Travel>) {
        if (travels.isEmpty()) {
            textViewNoTravelsFound.isVisible = true
            recyclerViewTravels.isVisible = false
        } else {
            textViewNoTravelsFound.isVisible = false
            recyclerViewTravels.isVisible = true
            adapter.submitItems(travels.toMutableList())
        }
    }

    private fun setExpensesSum(expensesSum: Double) {
        textViewTotalCost.text = getString(R.string.cost, expensesSum)
    }

    private fun setLoading(loading: Boolean) {
        progressBarTravelsCost.isVisible = loading
        progressBarTravels.isVisible = loading
        textViewTotalCost.isVisible = !loading
    }

    private fun onSearchTravels() {
        inputBottomSheet =
            SearchTravelsBottomSheet()
        inputBottomSheet?.show(supportFragmentManager, "SearchTravelsBottomSheet")
    }

    private fun navigateToTravelDetails(extra: Bundle) {
        val intent = Intent(this, TravelDetailsActivity::class.java).apply {
            putExtras(extra)
        }
        startActivity(intent)
    }
}
