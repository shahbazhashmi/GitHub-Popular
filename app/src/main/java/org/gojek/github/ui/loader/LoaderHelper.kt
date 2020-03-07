package org.gojek.github.ui.loader

import org.gojek.github.repository.api.network.Status


/**
 * Created by Shahbaz Hashmi on 2020-03-07.
 */
class LoaderHelper {

    val loaderViewModel by lazy {
        LoaderViewModel()
    }

    fun setRetryListener(retryClick: () -> Unit) {
        loaderViewModel.retryClick = retryClick
    }

    fun dismiss() {
        loaderViewModel.loaderState.set(Status.SUCCESS)
        loaderViewModel.isVisible.set(false)
    }

    fun showLoading() {
        loaderViewModel.loaderState.set(Status.LOADING)
        loaderViewModel.isVisible.set(true)
    }

    fun showError(errorText: String?) {
        loaderViewModel.text.set(errorText)
        loaderViewModel.loaderState.set(Status.ERROR)
        loaderViewModel.isVisible.set(true)
    }

}