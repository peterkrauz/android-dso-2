package com.peterkrauz.trab_dso2.presentation.common.paging

import androidx.lifecycle.observe
import com.peterkrauz.trab_dso2.R
import com.peterkrauz.trab_dso2.presentation.common.bottomsheet.InputBottomSheet
import com.peterkrauz.trab_dso2.presentation.common.base.BaseActivity
import com.peterkrauz.trab_dso2.utils.extensions.toast

abstract class PaginatingActivity<T> : BaseActivity() {

    var inputBottomSheet: InputBottomSheet? = null

    abstract val adapter: PagedAdapter<T>
    abstract val viewModel: PaginatingViewModel<T>

    open fun setupObservers() {
        viewModel.paginateLiveEvent.observe(this, ::notifyPaginated)
        viewModel.clearItemsLiveEvent.observe(this) { clearItems() }
        viewModel.pagedToEndLiveEvent.observe(this) { notifyEndOfPages() }
    }

    private fun notifyPaginated(page: Int) {
        toast(getString(R.string.reading_page, page))
    }

    private fun clearItems() {
        adapter.clearItems()
    }

    private fun notifyEndOfPages() {
        toast(getString(R.string.paged_to_end_please_re_search))
    }

}