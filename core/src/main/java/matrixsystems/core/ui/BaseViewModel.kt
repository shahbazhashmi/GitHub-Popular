package matrixsystems.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * Created by Shahbaz Hashmi on 23/04/21.
 */
open class BaseViewModel : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext
}