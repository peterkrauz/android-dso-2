package com.peterkrauz.trab_dso2.presentation.common

import com.peterkrauz.trab_dso2.utils.SingleLiveEvent

abstract class PaginatorViewModel<T> : BaseViewModel() {

    abstract var pageSize: Int
    abstract var pageNumber: Int
    abstract var currentPage: List<T>

    val paginateLiveEvent = SingleLiveEvent<Int>()
    val pagedToEndLiveEvent = SingleLiveEvent<Unit>()
    val clearItemsLiveEvent = SingleLiveEvent<Unit>()

    abstract fun paginate()
}