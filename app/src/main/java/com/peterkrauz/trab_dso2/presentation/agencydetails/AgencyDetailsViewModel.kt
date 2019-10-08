package com.peterkrauz.trab_dso2.presentation.agencydetails

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.peterkrauz.trab_dso2.Injector
import com.peterkrauz.trab_dso2.data.entities.PublicAgency
import com.peterkrauz.trab_dso2.data.entities.Travel
import com.peterkrauz.trab_dso2.data.repositories.TravelRepository
import com.peterkrauz.trab_dso2.presentation.agencydetails.bottomsheet.TravelFieldErrorType.*
import com.peterkrauz.trab_dso2.presentation.agencydetails.bottomsheet.TravelFieldsErrorBody
import com.peterkrauz.trab_dso2.presentation.common.paging.PaginatingViewModel
import com.peterkrauz.trab_dso2.utils.IntentExtras
import com.peterkrauz.trab_dso2.utils.SingleLiveEvent
import com.peterkrauz.trab_dso2.utils.extensions.*
import kotlinx.coroutines.launch

const val EMPTY_VALUE = 0.0

class AgencyDetailsViewModel(
    private val agency: PublicAgency,
    private val travelRepository: TravelRepository = Injector.travelRepository
) : PaginatingViewModel<Travel>() {

    private var travelExpensesSum: Double = EMPTY_VALUE
        set(value) {
            field = value
            travelExpensesSumLiveData.value = value
        }

    private var currentTravelExpenses: Double = EMPTY_VALUE
        set(value) {
            field = value
            travelExpensesSum += value
        }

    override var pageSize: Int = 15
    override var pageNumber: Int = 1
    override var currentPage: List<Travel> = emptyList()
        set(value) {
            field = value
            travelsLiveData.value = value
            updateExpenses()
        }

    lateinit var datePeriodToSearch: TravelsSearchBody

    val travelsLiveData = MutableLiveData<List<Travel>>()
    val searchTravelsLiveEvent = SingleLiveEvent<Unit>()
    val travelClickedLiveEvent = SingleLiveEvent<Bundle>()
    val travelExpensesSumLiveData = MutableLiveData<Double>()

    val validationCompleteLiveEvent = SingleLiveEvent<Unit>()
    val travelsTextErrorLiveData = MutableLiveData<TravelFieldsErrorBody>()

    init {
        travelExpensesSum = EMPTY_VALUE
    }

    fun onSearchTravels() {
        searchTravelsLiveEvent.call()
    }

    private fun onSearchFieldError(errorBody: TravelFieldsErrorBody) {
        travelsTextErrorLiveData.value = errorBody
    }

    private fun searchTravels(searchBody: TravelsSearchBody) {
        travelsTextErrorLiveData.value = TravelFieldsErrorBody.noErrorBody()
        clearItemsLiveEvent.call()

        currentTravelExpenses = EMPTY_VALUE
        datePeriodToSearch = searchBody
        pageNumber = 1

        viewModelScope.launch(errorHandler) {
            loadingLiveData.value = true
            currentPage = travelRepository.getAllInsidePeriod(
                searchBody.startDateFrom,
                searchBody.startDateUntil,
                searchBody.endDateFrom,
                searchBody.endDateUntil,
                agency.code
            )
            loadingLiveData.value = false
        }
    }

    // TODO("break this method in 3")
    fun validateAndSearch(
        startDateFrom: String,
        startDateUntil: String,
        endDateFrom: String,
        endDateUntil: String
    ) {
        var noErrors = true
        var startDateFromError = NO_ERROR
        var startDateUntilError = NO_ERROR
        var endDateFromError = NO_ERROR
        var endDateUntilError = NO_ERROR

        // checking for no blank field
        if (startDateFrom.isBlank()) {
            startDateFromError = BLANK_FIELD
            noErrors = false
        }

        if (startDateUntil.isBlank()) {
            startDateUntilError = BLANK_FIELD
            noErrors = false
        }

        if (endDateFrom.isBlank()) {
            endDateFromError = BLANK_FIELD
            noErrors = false
        }

        if (endDateUntil.isBlank()) {
            endDateUntilError = BLANK_FIELD
            noErrors = false
        }
        // endregion

        // clearing all '/' from strings
        val newStartDateFrom = startDateFrom.clearSlashes()
        val newStartDateUntil = startDateUntil.clearSlashes()
        val newEndDateFrom = endDateFrom.clearSlashes()
        val newEndDateUntil = endDateUntil.clearSlashes()
        // endregion

        // checking for valid formats
        if (!newStartDateFrom.isValidDateFormat()) {
            startDateFromError = INVALID_FORMAT
            noErrors = false
        }

        if (!newStartDateUntil.isValidDateFormat()) {
            startDateUntilError = INVALID_FORMAT
            noErrors = false
        }

        if (!newEndDateFrom.isValidDateFormat()) {
            endDateFromError = INVALID_FORMAT
            noErrors = false
        }

        if (!newEndDateUntil.isValidDateFormat()) {
            endDateUntilError = INVALID_FORMAT
            noErrors = false
        }
        // endregion

        // checking if the travels's ranges are inside the period of 1 month from each other
        if (
            startDateFromError == NO_ERROR &&
            startDateUntilError == NO_ERROR &&
            !isInValidRange(newStartDateFrom, newStartDateUntil)
        ) {
            startDateFromError = INVALID_RANGE
            startDateUntilError = INVALID_RANGE
        }

        if (
            endDateFromError == NO_ERROR &&
            endDateUntilError == NO_ERROR &&
            !isInValidRange(newEndDateFrom, newEndDateUntil)
        ) {
            endDateFromError = INVALID_RANGE
            endDateUntilError = INVALID_RANGE
        }
        // endregion

        if (noErrors) {
            searchTravels(
                TravelsSearchBody.getSearchBody(
                    newStartDateFrom,
                    newStartDateUntil,
                    newEndDateFrom,
                    newEndDateUntil
                )
            )
        } else {
            onSearchFieldError(
                TravelFieldsErrorBody(
                    startDateFromError,
                    startDateUntilError,
                    endDateFromError,
                    endDateUntilError
                )
            )
        }
    }

    private fun isInValidRange(dateFrom: String, dateUntil: String): Boolean {
        return if (dateUntil.month() == dateFrom.month()) {
            dateUntil.day() - dateFrom.day() in 0..1
        } else {
            dateUntil.month() - dateFrom.month() == 1
        }
    }

    fun onTravelClick(travel: Travel) {
        travelClickedLiveEvent.value = bundleOf(
            IntentExtras.EXTRA_TRAVEL to travel
        )
    }

    override fun paginate() {
        if (currentPage.size % pageSize != 0) {
            pagedToEndLiveEvent.call()
        } else {
            paginateLiveEvent.value = ++pageNumber

            viewModelScope.launch(errorHandler) {
                loadingLiveData.value = true
                currentPage = travelRepository.getAllInsidePeriod(
                    datePeriodToSearch.startDateFrom,
                    datePeriodToSearch.startDateUntil,
                    datePeriodToSearch.endDateFrom,
                    datePeriodToSearch.endDateUntil,
                    agency.code,
                    pageNumber
                )
                pageSize = currentPage.size
                loadingLiveData.value = false
            }
        }
    }

    private fun updateExpenses() {
        var currentSum = EMPTY_VALUE
        currentPage.forEach {
            currentSum += it.totalCost
        }
        currentTravelExpenses = currentSum
        // todo: try using some lambda for this
    }

}