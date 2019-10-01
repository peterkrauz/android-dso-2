package com.peterkrauz.trab_dso2.data.remote.builders

import okhttp3.OkHttpClient

object OkHttpClientBuilder {
    fun build(): OkHttpClient = OkHttpClient.Builder().build()
}