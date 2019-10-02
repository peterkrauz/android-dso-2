package com.peterkrauz.trab_dso2.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterkrauz.trab_dso2.Injector
import com.peterkrauz.trab_dso2.data.entities.PublicAgency
import com.peterkrauz.trab_dso2.data.repositories.PublicAgencyRepository
import com.peterkrauz.trab_dso2.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class PublicAgenciesViewModel(
    private val agencyRepository: PublicAgencyRepository = Injector.publicAgencyRepository
) : ViewModel() {

    private var page: Int = 1

    val publicAgenciesLiveData = MutableLiveData<List<PublicAgency>>()
    val searchAgenciesLiveEvent = SingleLiveEvent<Unit>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val errorLiveEvent = SingleLiveEvent<Throwable>()

    private val errorHandler = CoroutineExceptionHandler { _, error -> handleError(error) }

    init {
        viewModelScope.launch(errorHandler) {
            loadingLiveData.value = true
            publicAgenciesLiveData.value = agencyRepository.getAll(page)
            loadingLiveData.value = false
        }
    }

    private fun handleError(error: Throwable) {
        loadingLiveData.value = false
        errorLiveEvent.value = error
    }

    fun searchAgencies() {
        searchAgenciesLiveEvent.call()
    }

}