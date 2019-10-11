package com.peterkrauz.trab_dso2.presentation.publicagencies

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.peterkrauz.trab_dso2.Injector
import com.peterkrauz.trab_dso2.data.entities.PublicAgency
import com.peterkrauz.trab_dso2.data.repositories.PublicAgencyRepository
import com.peterkrauz.trab_dso2.presentation.common.paging.PaginatingViewModel
import com.peterkrauz.trab_dso2.utils.IntentExtras
import com.peterkrauz.trab_dso2.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class PublicAgenciesViewModel(
    private val agencyRepository: PublicAgencyRepository = Injector.publicAgencyRepository
) : PaginatingViewModel<PublicAgency>() {

    override var pageSize: Int = 15
    override var pageNumber: Int = 1
    override var currentPage: List<PublicAgency> = emptyList()
        set(value) {
            field = value
            publicAgenciesLiveData.value = value
        }

    private var descriptionToSearch: String = ""

    val publicAgenciesLiveData = MutableLiveData<List<PublicAgency>>()
    val searchAgenciesLiveEvent = SingleLiveEvent<Unit>()
    val agencyClickedLiveEvent = SingleLiveEvent<Bundle>()

    val publicAgencyTextErrorLiveData = MutableLiveData<Boolean>()

    fun onSearchAgencies() {
        searchAgenciesLiveEvent.call()
    }

    fun onSearchFieldError() {
        publicAgencyTextErrorLiveData.value = true
    }

    fun searchAgencies(description: String) {
        publicAgencyTextErrorLiveData.value = false
        clearItemsLiveEvent.call()
        descriptionToSearch = description
        pageNumber = 1

        viewModelScope.launch(errorHandler) {
            loadingLiveData.value = true
            currentPage = agencyRepository.searchByDescription(descriptionToSearch)
            loadingLiveData.value = false
        }
    }

    fun onAgencyClick(agency: PublicAgency) {
        agencyClickedLiveEvent.value = bundleOf(
            IntentExtras.EXTRA_AGENCY to agency
        )
    }

    override fun paginate() {
        if (currentPage.size % pageSize != 0) {
            pagedToEndLiveEvent.call()
        } else {
            pageNumber++
            paginateLiveEvent.value = pageNumber

            viewModelScope.launch(errorHandler) {
                loadingLiveData.value = true
                currentPage = agencyRepository.searchByDescription(
                    descriptionToSearch,
                    pageNumber
                )
                loadingLiveData.value = false
            }
        }
    }

}