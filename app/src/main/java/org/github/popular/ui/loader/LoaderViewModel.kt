package org.github.popular.ui.loader

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import org.github.popular.repository.api.network.Status


/**
 * Created by Shahbaz Hashmi on 2020-03-07.
 */
class LoaderViewModel : ViewModel() {
    var loaderState: ObservableField<Status> = ObservableField()
    var errorTitle: ObservableField<String> = ObservableField()
    var errorDescription: ObservableField<String> = ObservableField()
    var isVisible: ObservableField<Boolean> = ObservableField()

    var retryClick: () -> Unit = {}

    fun onRetryClick() {
        retryClick()
    }
}