package com.peterkrauz.trab_dso2.presentation.agencydetails

import com.peterkrauz.trab_dso2.utils.extensions.day
import com.peterkrauz.trab_dso2.utils.extensions.month
import com.peterkrauz.trab_dso2.utils.extensions.year

class TravelsSearchBody(
    val startDateFrom: String,
    val startDateUntil: String,
    val endDateFrom: String,
    val endDateUntil: String
) {
    companion object {
        fun getSearchBody(
            startDateFrom: String,
            startDateUntil: String,
            endDateFrom: String,
            endDateUntil: String
        ): TravelsSearchBody {
            val formattedStartDateFrom = with(startDateFrom) {
                val day: String = if (day() in 1..9) {
                    "0${day()}"
                } else {
                    day().toString()
                }

                val month: String = if (month() in 1..9) {
                    "0${month()}"
                } else {
                    month().toString()
                }
                "$day/$month/${year()}"
            }
            val formattedStartDateUntil = with(startDateUntil) {
                val day: String = if (day() in 1..9) {
                    "0${day()}"
                } else {
                    day().toString()
                }

                val month: String = if (month() in 1..9) {
                    "0${month()}"
                } else {
                    month().toString()
                }
                "$day/$month/${year()}"
            }
            val formattedEndDateFrom = with(endDateFrom) {
                val day: String = if (day() in 1..9) {
                    "0${day()}"
                } else {
                    day().toString()
                }

                val month: String = if (month() in 1..9) {
                    "0${month()}"
                } else {
                    month().toString()
                }
                "$day/$month/${year()}"
            }
            val formattedEndDateUntil = with(endDateUntil) {
                val day: String = if (day() in 1..9) {
                    "0${day()}"
                } else {
                    day().toString()
                }

                val month: String = if (month() in 1..9) {
                    "0${month()}"
                } else {
                    month().toString()
                }
                "$day/$month/${year()}"
            }

            return TravelsSearchBody(
                formattedStartDateFrom,
                formattedStartDateUntil,
                formattedEndDateFrom,
                formattedEndDateUntil
            )
        }
    }
}