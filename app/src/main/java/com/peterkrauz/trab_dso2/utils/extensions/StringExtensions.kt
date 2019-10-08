package com.peterkrauz.trab_dso2.utils.extensions

fun String.day(): Int {
    return this.substring(0, 2).toInt()
}

fun String.month(): Int {
    return this.substring(2, 4).toInt()
}

fun String.year(): Int {
    return this.substring(4, 8).toInt()
}

fun String.clearSlashes(): String {
    return if (this.contains("/")) {
        this.split("/").joinToString("")
    } else {
        this
    }
}

fun String.isValidDateFormat(): Boolean {
    if (length != 8) {
        return false
    } else {
        val day = "${day()}"
        val month = "${month()}"
        val year = "${year()}"

        return if (day.length in 1..2) {
            if (month.length in 1..2) {
                if (year.length == 4) {
                    day() in 1..31 &&
                            month() in 1..12 &&
                            year() in 1980..2019
                } else {
                    false
                }
            } else {
                false
            }
        } else {
            false
        }
    }
}