package com.peterkrauz.trab_dso2.data.entities

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Travel(
    @Json(name = "pessoa")
    val person: Person,
    @Json(name = "dataInicioAfastamento")
    val startDate: String,
    @Json(name = "dataFimAfastamento")
    val endDate: String,
    @Json(name = "valorTotalViagem")
    val cost: Double
): Parcelable {
    val personName: String
        get() = person.name
}


@Parcelize
@JsonClass(generateAdapter = true)
data class Person(
    @Json(name = "nome")
    val name: String
): Parcelable