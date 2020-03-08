package org.gojek.github.ui.githubrepo

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.gojek.github.R
import org.gojek.github.clickChildViewWithId
import org.gojek.github.waitId
import org.gojek.github.withCustomConstraints
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Shahbaz Hashmi on 2020-03-08.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class GithubRepoActivityBehaviorTest {

    @get:Rule
    var activityRule: ActivityTestRule<GithubRepoActivity> =
        ActivityTestRule(GithubRepoActivity::class.java)

    @Test
    fun loadData_sameActivity() {
        onView(isRoot()).perform(waitId(R.id.recyclerview_repo, 5000)).perform().check(
            matches(
                isDisplayed()
            )
        )
        Thread.sleep(5000)
    }

    @Test
    fun doPtr_sameActivity() {
        onView(withId(R.id.swipe_container))
            .perform(withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)))
        Thread.sleep(10000)
    }

    @Test
    fun doScroll_sameActivity() {
        onView(withId(R.id.recyclerview_repo))
            .perform(withCustomConstraints(swipeUp(), isDisplayingAtLeast(100)))
        Thread.sleep(5000)
    }

    @Test
    fun selectListItem_sameActivity() {
        onView(withId(R.id.recyclerview_repo)).perform(
            RecyclerViewActions.actionOnItemAtPosition<GithubRepoAdapter.RepoHolder>(
                0,
                clickChildViewWithId(R.id.row_parent_lt)
            )
        )
        Thread.sleep(2000)
    }


}