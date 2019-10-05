package com.peterkrauz.trab_dso2.data.repositories

import com.peterkrauz.trab_dso2.Injector
import com.peterkrauz.trab_dso2.data.entities.Travel
import com.peterkrauz.trab_dso2.data.remote.api.TravelApi

class TravelRepository(
    private val api: TravelApi = Injector.travelApi
) {

    suspend fun getAllInsidePeriod(
        startDateFrom: String,
        startDateUntil: String,
        endDateFrom: String,
        endDateUntil: String,
        agencyCode: String,
        page: Int = 1
    ): List<Travel> {
        return api.getAllInsidePeriod(
            startDateFrom,
            startDateUntil,
            endDateFrom,
            endDateUntil,
            agencyCode,
            page
        )
    }

}