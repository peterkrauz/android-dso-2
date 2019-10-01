package com.peterkrauz.trab_dso2.data.remote

sealed class Result<out S, out E: Throwable>

data class Success<out S>(val value: S) : Result<S, Nothing>()
data class Failure<out E: Throwable>(val value: E) : Result<Nothing, Throwable>()