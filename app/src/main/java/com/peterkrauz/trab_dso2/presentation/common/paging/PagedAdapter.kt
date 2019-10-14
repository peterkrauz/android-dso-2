package com.peterkrauz.trab_dso2.presentation.common.paging

interface PagedAdapter<T> {
    fun submitItems(items: MutableList<T>)
    fun clearItems()
}