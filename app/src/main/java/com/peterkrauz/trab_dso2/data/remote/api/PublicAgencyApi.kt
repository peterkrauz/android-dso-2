package com.peterkrauz.trab_dso2.data.remote.api

import com.peterkrauz.trab_dso2.data.entities.PublicAgency
import retrofit2.http.GET
import retrofit2.http.Query

interface PublicAgencyApi {

    @GET("api-de-dados/orgaos-siafi")
    suspend fun getAll(@Query("pagina") page: Int): List<PublicAgency>

    @GET("api-de-dados/orgaos-siafi")
    suspend fun searchByDescription(
        @Query("descricao")description: String,
        @Query("pagina") page: Int
    ): List<PublicAgency>

}