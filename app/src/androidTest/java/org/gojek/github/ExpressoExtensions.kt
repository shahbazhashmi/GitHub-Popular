package org.github.popular

import android.view.View
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.Matcher
import java.util.concurrent.TimeoutException


/**
 * Created by Shahbaz Hashmi on 2020-03-08.
 */

/**
 * Perform action of waiting for a specific view id.
 * @param viewId The id of the view to wait for.
 * @param millis The timeout of until when to wait for.
 */
fun waitId(viewId: Int, millis: Long): ViewAction? {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return isRoot()
        }

        override fun getDescription(): String {
            return "wait for a specific view with id <$viewId> during $millis millis."
        }

        override fun perform(uiController: UiController, view: View?) {
            uiController.loopMainThreadUntilIdle()
            val startTime = System.currentTimeMillis()
            val endTime = startTime + millis
            val viewMatcher: Matcher<View> = withId(viewId)
            do {
                for (child in TreeIterables.breadthFirstViewTraversal(view)) { // found view with required ID
                    if (viewMatcher.matches(child)) {
                        return
                    }
                }
                uiController.loopMainThreadForAtLeast(50)
            } while (System.currentTimeMillis() < endTime)
            throw PerformException.Builder()
                .withActionDescription(this.description)
                .withViewDescription(HumanReadables.describe(view))
                .withCause(TimeoutException())
                .build()
        }
    }
}


fun withCustomConstraints(
    action: ViewAction,
    constraints: Matcher<View>
): ViewAction? {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return constraints
        }

        override fun getDescription(): String {
            return action.description
        }

        override fun perform(
            uiController: UiController,
            view: View
        ) {
            action.perform(uiController, view)
        }
    }
}


fun clickChildViewWithId(id: Int): ViewAction? {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View>? {
            return null
        }

        override fun getDescription(): String {
            return "Click on a child view with specified id."
        }

        override fun perform(
            uiController: UiController,
            view: View
        ) {
            val v = view.findViewById<View>(id)
            v.performClick()
        }
    }
}