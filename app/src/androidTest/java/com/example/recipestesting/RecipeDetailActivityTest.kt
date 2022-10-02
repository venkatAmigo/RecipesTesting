package com.example.recipestesting

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.recipestesting.models.NutrientInfo
import com.example.recipestesting.models.Recipe
import com.example.recipestesting.models.TotalNutrients
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class RecipeDetailActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(RecipeDetailActivity::class.java)

    lateinit var activity: RecipeDetailActivity

    @Before
    fun setUp() {
        val recipeOne = Recipe(
            label = "Chicken Roast", image = "", totalNutrients = TotalNutrients
                (
                ENERC_KCAL = NutrientInfo("Energy", 1.0, "mg"), FAT = NutrientInfo(
                "Fat", 2.0,
                "mg"
            ),
                SUGAR = NutrientInfo("Sugar", 3.0, "mg")
            ), ingredientLines = listOf("1/2 cup of chicken", "1 tablespoon dried oregano")
        )
        mActivityScenarioRule.scenario.onActivity {
            activity = it
            it.recipe = recipeOne
        }
    }

    @Test
    fun checkWhatsappShareButtonDisplayed() {
        val imageButton = onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.fab),
                isDisplayed()
            )
        )
        imageButton.check(matches(isDisplayed()))
    }

    @Test
    fun checkIngredientsDisplayed() {
        onView(withText("1/2 cup of chicken")).check(matches(isDisplayed()))
        onView(withText("1 tablespoon dried oregano")).check(matches(isDisplayed()))
    }

    @Test
    fun checkNutrientsDisplayed() {
        onView(withText("Energy, 1.0 mg")).check(matches(isDisplayed()))
        onView(withText("Fat, 2.0 mg")).check(matches(isDisplayed()))
        onView(withText("Sugar, 3.0 mg")).check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
    }
}