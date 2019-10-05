package com.peterkrauz.trab_dso2.presentation.common.paging

import com.peterkrauz.trab_dso2.presentation.common.base.BaseViewModel
import com.peterkrauz.trab_dso2.utils.SingleLiveEvent

abstract class PaginatingViewModel<T> : BaseViewModel() {

    abstract var pageSize: Int
    abstract var pageNumber: Int
    abstract var currentPage: List<T>

    val paginateLiveEvent = SingleLiveEvent<Int>()
    val pagedToEndLiveEvent = SingleLiveEvent<Unit>()
    val clearItemsLiveEvent = SingleLiveEvent<Unit>()

    abstract fun paginate()
}