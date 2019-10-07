package com.peterkrauz.trab_dso2.presentation.agencydetails.bottomsheet

class TravelFieldsErrorBody(
    val startDateFromError: TravelFieldErrorType,
    val startDateUntilError: TravelFieldErrorType,
    val endDateFromError: TravelFieldErrorType,
    val endDateUntilError: TravelFieldErrorType
) {
    companion object {
        fun noErrorBody() = TravelFieldsErrorBody(
            TravelFieldErrorType.NO_ERROR,
            TravelFieldErrorType.NO_ERROR,
            TravelFieldErrorType.NO_ERROR,
            TravelFieldErrorType.NO_ERROR
        )
    }
}