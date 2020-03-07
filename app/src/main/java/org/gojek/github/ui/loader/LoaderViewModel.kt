package org.gojek.github.ui.loader

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import org.gojek.github.repository.api.network.Status


/**
 * Created by Shahbaz Hashmi on 2020-03-07.
 */
class LoaderViewModel : ViewModel() {
    var loaderState: ObservableField<Status> = ObservableField()
    var text: ObservableField<String> = ObservableField()
    var isVisible : ObservableField<Boolean> = ObservableField()

    var retryClick: () -> Unit = {}

    fun onRetryClick() {
        retryClick()
    }
}