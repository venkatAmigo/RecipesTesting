package com.example.recipestesting

import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.recipestesting.models.*
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        mActivityScenarioRule.scenario.onActivity {
            var recipes = mutableListOf<Hit>()
            val recipeOne = Recipe(label = "Chicken", image = "", totalNutrients = TotalNutrients
                (ENERC_KCAL = NutrientInfo("",0.0,""),FAT=NutrientInfo("",0.0,""),
                SUGAR = NutrientInfo("",0.0,"")), ingredientLines = listOf("1/2 cup of chicken", "1 tablespoon dried oregano")
            )
            val recipeTwo = Recipe(label = "Pepper Fry", image = "", totalNutrients = TotalNutrients
                (ENERC_KCAL = NutrientInfo("",0.0,""),FAT=NutrientInfo("",0.0,""),
                SUGAR = NutrientInfo("",0.0,"")), ingredientLines = listOf("1/2 cup of pepper", "" +
                    " +"+"1 tablespoon dried pepper")
            )

            recipes.add(Hit(LinksX(Self("","")),recipeOne))
            recipes.add(Hit(LinksX(Self("","")),recipeTwo))
            it.recipeList = recipes
            it.recipesAdapter = RecipesAdapter(it.recipeList)
            it.recipeRecyclerView.adapter = it.recipesAdapter
        }
    }

    @After
    fun tearDown() {
    }
    @Test
    fun isSearchViewPresent(){
        val searchView = onView(withId(R.id.searchView))
        searchView.check(matches(isDisplayed()))
    }

    @Test
    fun testSearch(){
        val searchView = onView(withId(R.id.searchView))
        searchView.perform(typeText("pepper fry")).perform(pressKey(KeyEvent.KEYCODE_ENTER))
        //On typing check whether only Pepper fry displayed not Chicken
        onView(withText("Pepper Fry")).check(matches(isDisplayed()))
        onView(withText("Chicken")).check(doesNotExist())
    }

    @Test
    fun checkItemInRecyclerView(){
        //Cheeking whether given recipe displayed in Recyclerview
        val textView = onView(
            Matchers.allOf(
                withId(R.id.recipe_name), withText("Chicken"),
                withParent(withParent(IsInstanceOf.instanceOf(FrameLayout::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Chicken")))
    }

    @Test
    fun checkOnClickItemOpensDetailsActivity(){
        val recyclerView = onView(
            Matchers.allOf(
                withId(R.id.recipes_recycler),
                childAtPosition(
                    ViewMatchers.withClassName(Matchers.`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        //Checking whether clicking on recipe navigating and showing ingredients
        val textView = onView(
            Matchers.allOf(
                withId(android.R.id.text1), withText("1/2 cup of chicken"),
                withParent(
                    Matchers.allOf(
                        withId(R.id.ingredients_list),
                        withParent(IsInstanceOf.instanceOf(ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("1/2 cup of chicken")))

    }
    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}