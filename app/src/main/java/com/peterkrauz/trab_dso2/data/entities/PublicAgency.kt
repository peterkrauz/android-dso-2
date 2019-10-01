package com.peterkrauz.trab_dso2.data.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PublicAgency(
    @Json(name = "codigo")
    val code: String,
    @Json(name = "descricao")
    val description: String,
    @Json(name = "codigoDescricaoFormatado")
    val formattedDescriptionCode: String
)