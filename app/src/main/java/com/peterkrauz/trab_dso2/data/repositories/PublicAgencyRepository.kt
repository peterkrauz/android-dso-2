package com.peterkrauz.trab_dso2.data.repositories

import com.peterkrauz.trab_dso2.Injector
import com.peterkrauz.trab_dso2.data.remote.api.PublicAgencyApi

class PublicAgencyRepository (
    private val api: PublicAgencyApi = Injector.publicAgencyApi
) {

    fun findAll(page: Int = 1) = api.findAll(page)

}