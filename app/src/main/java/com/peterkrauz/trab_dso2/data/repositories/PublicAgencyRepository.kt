package com.peterkrauz.trab_dso2.data.repositories

import com.peterkrauz.trab_dso2.Injector
import com.peterkrauz.trab_dso2.data.entities.PublicAgency
import com.peterkrauz.trab_dso2.data.remote.api.PublicAgencyApi

class PublicAgencyRepository (
    private val api: PublicAgencyApi = Injector.publicAgencyApi
) {

    suspend fun getAll(page: Int = 1): List<PublicAgency> {
        return api.getAll(page)
    }

    suspend fun searchByDescription(description: String, page: Int = 1): List<PublicAgency> {
        return api.searchByDescription(description, page)
    }

}