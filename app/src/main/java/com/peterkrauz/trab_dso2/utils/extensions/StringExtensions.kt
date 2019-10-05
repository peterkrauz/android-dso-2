package com.peterkrauz.trab_dso2.utils.extensions


// 0 1 2 3 4 5 6 7 8 9
// D D / M M / A A A A
fun String.month(): Int {
    return this.substring(3, 5).toInt()
}

fun String.day(): Int {
    return this.substring(0, 2).toInt()
}

fun String.year(): Int {
    return this.substring(6, 10).toInt()
}

fun String.isValidDateFormat(): Boolean {
    if (length != 10) {
        return false
    } else {
        val day = "${day()}"
        val month = "${month()}"
        val year = "${year()}"

        return if (day.length == month.length && day.length == 2) {
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
    }
}