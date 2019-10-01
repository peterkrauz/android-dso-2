package com.peterkrauz.trab_dso2.data.remote.builders

import com.squareup.moshi.Moshi

object MoshiBuilder {

    fun build(): Moshi = Moshi.Builder().build()

}