package com.peterkrauz.trab_dso2.data.remote.builders

import com.peterkrauz.trab_dso2.BuildConfig
import com.peterkrauz.trab_dso2.Injector
import okhttp3.OkHttpClient
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitBuilder(
    private val moshi: Moshi = Injector.moshi,
    private val client: OkHttpClient = Injector.okHttpClient
) {

    fun build(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(OkHttpClient.Builder.build())
            .build()
    }

}