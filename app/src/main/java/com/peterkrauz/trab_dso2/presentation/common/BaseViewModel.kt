package com.peterkrauz.trab_dso2.presentation.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peterkrauz.trab_dso2.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel() {

    open val loadingLiveData = MutableLiveData<Boolean>()
    val errorLiveEvent = SingleLiveEvent<Throwable>()
    val errorHandler = CoroutineExceptionHandler { _, error -> handleError(error) }

    open fun handleError(error: Throwable) {
        loadingLiveData.value = false
        errorLiveEvent.value = error
    }

}