package matrixsystems.core.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import matrixsystems.core.di.base.Injectable
import javax.inject.Inject

/**
 * Created by Shahbaz Hashmi on 23/04/21.
 */
open class BaseFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
}