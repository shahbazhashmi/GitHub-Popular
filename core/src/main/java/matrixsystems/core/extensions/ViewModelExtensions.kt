package matrixsystems.core.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import matrixsystems.core.ui.BaseActivity
import matrixsystems.core.ui.BaseFragment

/**
 * Created by Shahbaz Hashmi on 2020-03-06.
 */


/**
 * Synthetic sugaring to get instance of [ViewModel] for [AppCompatActivity].
 */
inline fun <reified T : ViewModel> BaseActivity.getViewModel(): T {
    return ViewModelProvider(this, viewModelFactory).get(T::class.java)
}

/**
 * Synthetic sugaring to get instance of [ViewModel] for [Fragment].
 */
inline fun <reified T : ViewModel> BaseFragment.getViewModel(): T {
    return ViewModelProvider(this, viewModelFactory).get(T::class.java)
}