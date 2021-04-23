package matrixsystems.core.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import matrixsystems.core.connectivty.ConnectionStateMonitor
import matrixsystems.core.constants.SystemVariables
import matrixsystems.core.utils.LogHelper
import javax.inject.Inject

/**
 * Created by Shahbaz Hashmi on 2020-03-06.
 */
abstract class BaseActivity : AppCompatActivity(), HasAndroidInjector {

    private val TAG = "BaseActivity"

    val TRANSACTION_DEFAULT = "default"

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun androidInjector() = dispatchingAndroidInjector

    abstract val contentMain: Int?

    fun addFragment(fragment: Fragment?, addToBackstack: Boolean, backStackTag: String?) {
        try {
            if (contentMain == null) {
                throw NullPointerException("Container not implemented in parent activity")
            }
            if (fragment == null) {
                throw NullPointerException("Fragment is null")
            }
            val ft = supportFragmentManager.beginTransaction()
            ft.add(contentMain!!, fragment)
            if (addToBackstack) {
                ft.addToBackStack(backStackTag)
            }
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            ft.commit()
        } catch (e: Exception) {
            LogHelper.e(TAG, e)
        }

    }

    fun replaceFragment(fragment: Fragment?, addToBackstack: Boolean, backStackTag: String?) {
        try {
            if (contentMain == null) {
                throw NullPointerException("Container not implemented in parent activity")
            }
            if (fragment == null) {
                throw NullPointerException("Fragment is null")
            }
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(contentMain!!, fragment)
            if (addToBackstack) {
                ft.addToBackStack(backStackTag)
            }
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            ft.commit()
        } catch (e: Exception) {
            LogHelper.e(TAG, e)
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(menuItem)
    }

    override fun onBackPressed() {
        onBackPressedApp()
    }

    fun onBackPressedApp() {
        val manager = supportFragmentManager
        if (manager.backStackEntryCount == 1) {
            // If that's the only entry left, finish the app.
            this.finish()
            return
        }
        if (manager.backStackEntryCount > 0) {
            // Pop all entries till a non POPUP state
            val popped = manager.popBackStackImmediate(
                TRANSACTION_DEFAULT,
                0
            )
            if (popped)
                return
        }
        super.onBackPressed()
    }


    fun clearBackStack() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ConnectionStateMonitor(this).observeForever {
            SystemVariables.isInternetConnected = it
        }
    }
}