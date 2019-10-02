package com.peterkrauz.trab_dso2

import com.peterkrauz.trab_dso2.data.remote.api.PublicAgencyApi
import com.peterkrauz.trab_dso2.data.remote.builders.MoshiBuilder
import com.peterkrauz.trab_dso2.data.remote.builders.OkHttpClientBuilder
import com.peterkrauz.trab_dso2.data.remote.builders.RetrofitBuilder
import com.peterkrauz.trab_dso2.data.repositories.PublicAgencyRepository
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object Injector {

    private val retrofit: Retrofit by lazy { RetrofitBuilder.build() }

    val moshi: Moshi by lazy { MoshiBuilder.build() }

    val okHttpClient: OkHttpClient by lazy { OkHttpClientBuilder.build() }

    val publicAgencyApi: PublicAgencyApi by lazy { retrofit.create(PublicAgencyApi::class.java) }

    val publicAgencyRepository: PublicAgencyRepository by lazy { PublicAgencyRepository() }

}