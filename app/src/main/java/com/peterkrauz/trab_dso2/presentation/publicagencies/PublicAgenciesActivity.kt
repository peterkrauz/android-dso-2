package com.peterkrauz.trab_dso2.presentation.publicagencies

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.peterkrauz.trab_dso2.R
import com.peterkrauz.trab_dso2.data.entities.PublicAgency
import com.peterkrauz.trab_dso2.presentation.agencydetails.AgencyDetailsActivity
import com.peterkrauz.trab_dso2.presentation.common.paging.PaginatingActivity
import com.peterkrauz.trab_dso2.presentation.publicagencies.bottomsheet.SearchPublicAgenciesBottomSheet
import com.peterkrauz.trab_dso2.utils.lazyViewModel
import kotlinx.android.synthetic.main.activity_agency_details.*
import kotlinx.android.synthetic.main.activity_public_agencies.*
import kotlinx.android.synthetic.main.activity_public_agencies.toolbar

class PublicAgenciesActivity : PaginatingActivity<PublicAgency>() {

    override val adapter by lazy {
        PublicAgenciesAdapter {
            viewModel.onAgencyClick(it)
        }
    }

    override val viewModel by lazyViewModel {
        PublicAgenciesViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_public_agencies)

        setupToolbar()
        setupView()
        setupObservers()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)

        supportActionBar?.run {
            title = getString(R.string.federal_gov_agencies)
        }
    }

    private fun setupView() {
        recyclerViewPublicAgencies.apply {
            adapter = this@PublicAgenciesActivity.adapter
            layoutManager = LinearLayoutManager(this@PublicAgenciesActivity)
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
        fabSearchAgencies.setOnClickListener {
            viewModel.onSearchAgencies()
        }
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.publicAgenciesLiveData.observe(this, ::setPublicAgencies)
        viewModel.loadingLiveData.observe(this, ::setLoading)
        viewModel.searchAgenciesLiveEvent.observe(this) { onSearchAgencies() }
        viewModel.agencyClickedLiveEvent.observe(this, ::navigateToAgencyDetails)
    }

    private fun setPublicAgencies(publicAgencies: List<PublicAgency>) {
        if (publicAgencies.isEmpty()) {
            textViewNoAgenciesFound.isVisible = true
            recyclerViewPublicAgencies.isVisible = false
        } else {
            textViewNoAgenciesFound.isVisible = false
            recyclerViewPublicAgencies.isVisible = true
            adapter.submitItems(publicAgencies.toMutableList())
        }
    }

    private fun setLoading(loading: Boolean) {
        progressBarPublicAgencies.isVisible = loading
    }

    private fun onSearchAgencies() {
        inputBottomSheet =
            SearchPublicAgenciesBottomSheet()
        inputBottomSheet?.show(supportFragmentManager, "SearchAgenciesBottomSheet")
    }

    private fun navigateToAgencyDetails(extra: Bundle) {
        val intent = Intent(this, AgencyDetailsActivity::class.java).apply {
            putExtras(extra)
        }
        startActivity(intent)
    }
}
