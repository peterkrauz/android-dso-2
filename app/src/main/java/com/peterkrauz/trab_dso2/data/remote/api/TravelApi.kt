package com.peterkrauz.trab_dso2.data.remote.api

import com.peterkrauz.trab_dso2.data.entities.Travel
import retrofit2.http.GET
import retrofit2.http.Query

interface TravelApi {

    @GET("api-de-dados/viagens")
    suspend fun getAllInsidePeriod(
        @Query("dataIdaDe") startDateFrom: String,
        @Query("dataIdaAte") startDateUntil: String,
        @Query("dataRetornoDe") endDateFrom: String,
        @Query("dataRetornoAte") endDateUntil: String,
        @Query("codigoOrgao") agencyCode: String,
        @Query("pagina") page: Int
    ): List<Travel>

}