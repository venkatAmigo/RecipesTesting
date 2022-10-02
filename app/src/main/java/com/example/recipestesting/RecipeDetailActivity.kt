package com.example.recipestesting

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recipestesting.databinding.ActivityRecipeDetailBinding
import com.example.recipestesting.models.Recipe
import com.example.recipestesting.models.*

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding
    lateinit var recipe: Recipe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.getSerializableExtra("RECIPE") !=null)
        recipe = intent.getSerializableExtra("RECIPE") as Recipe
        else{ val recipeOne = Recipe(
            label = "Chicken", image = "", totalNutrients = TotalNutrients
                (
                ENERC_KCAL = NutrientInfo("Energy", 1.0, "mg"), FAT = NutrientInfo("Fat", 2.0,
                "mg"),
                SUGAR = NutrientInfo("Sugar", 3.0, "mg")
            ), ingredientLines = listOf("1/2 cup of chicken", "1 tablespoon dried oregano")
        )

            recipe = recipeOne
        }

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = recipe.label

        binding.scrollContent.ingredientsList?.adapter = ArrayAdapter(
            this, android.R.layout
                .simple_list_item_1, recipe.ingredientLines
        )

        val nutrients = recipe.totalNutrients
        val nuts = mutableListOf<String>()
        val fat = with(nutrients.FAT) {
            this.label + ", " + this.quantity + " " + this.unit
        }
        val energy = with(nutrients.ENERC_KCAL) {
            this.label + ", " + this.quantity + " " + this.unit
        }
        val sugar = with(nutrients.SUGAR) {
            this.label + ", " + this.quantity + " " + this.unit
        }
        nuts.add(fat)
        nuts.add(energy)
        nuts.add(sugar)
        binding.scrollContent.nutrientsList?.adapter = ArrayAdapter(
            this, android.R.layout
                .simple_list_item_1, nuts
        )
        binding.fab.setOnClickListener { view ->
            val sendIntent: Intent = Intent()
            sendIntent.setAction(Intent.ACTION_SEND)
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                recipe.label
            )
            sendIntent.setType("text/plain")
            //sendIntent.setPackage("com.whatsapp")
            startActivity(sendIntent)
        }
    }
}