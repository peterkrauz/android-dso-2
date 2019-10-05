package com.peterkrauz.trab_dso2.presentation.publicagencies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.peterkrauz.trab_dso2.R
import com.peterkrauz.trab_dso2.data.entities.PublicAgency
import com.peterkrauz.trab_dso2.presentation.agencydetails.AgencyDetailsActivity
import com.peterkrauz.trab_dso2.presentation.publicagencies.bottomsheet.SearchPublicAgenciesBottomSheet
import com.peterkrauz.trab_dso2.utils.extensions.toast
import com.peterkrauz.trab_dso2.utils.lazyViewModel
import kotlinx.android.synthetic.main.activity_public_agencies.*

class PublicAgenciesActivity : AppCompatActivity() {

    private var searchAgenciesBottomSheet: SearchPublicAgenciesBottomSheet? = null

    private val agenciesAdapter by lazy {
        PublicAgenciesAdapter {
            viewModel.onAgencyClick(it)
        }
    }

    private val viewModel by lazyViewModel {
        PublicAgenciesViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_public_agencies)

        setupToolbar()
        setupView()
        setupViewModel()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)

        supportActionBar?.run {
            title = getString(R.string.federal_gov_agencies)
        }
    }

    private fun setupView() {
        recyclerViewPublicAgencies.apply {
            adapter = agenciesAdapter
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

    private fun setupViewModel() {
        viewModel.publicAgenciesLiveData.observe(this, ::setPublicAgencies)
        viewModel.loadingLiveData.observe(this, ::setLoading)
        viewModel.searchAgenciesLiveEvent.observe(this) { onSearchAgencies() }
        viewModel.agencyClickedLiveEvent.observe(this, ::navigateToAgencyDetails)
        viewModel.paginateLiveEvent.observe(this, ::notifyPaginated)
        viewModel.clearItemsLiveEvent.observe(this) { clearAgencies() }
        viewModel.pagedToEndLiveEvent.observe(this) { notifyEndOfPages() }
    }

    // TODO("if there's time left, make recycler view have two types of viewholders. one of em has just the number of the pageNumber")
    private fun setPublicAgencies(publicAgencies: List<PublicAgency>) {
        if (publicAgencies.isEmpty()) {
            textViewNoAgenciesSearched.isVisible = true
        } else {
            textViewNoAgenciesSearched.isVisible = false
            agenciesAdapter.submitItems(publicAgencies.toMutableList())
        }
    }

    private fun setLoading(loading: Boolean) {
        progressBarPublicAgencies.isVisible = loading
    }

    private fun onSearchAgencies() {
        searchAgenciesBottomSheet =
            SearchPublicAgenciesBottomSheet()
        searchAgenciesBottomSheet?.show(supportFragmentManager, "SearchAgenciesBottomSheet")
    }

    private fun navigateToAgencyDetails(extra: Bundle) {
        val intent = Intent(this, AgencyDetailsActivity::class.java).apply {
            putExtras(extra)
        }
        startActivity(intent)
    }

    private fun notifyPaginated(page: Int) {
        toast(getString(R.string.paginated_to, page))
    }

    private fun clearAgencies() {
        agenciesAdapter.clearItems()
    }

    private fun notifyEndOfPages() {
        toast(getString(R.string.paged_to_end_please_re_search))
    }
}
