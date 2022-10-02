package com.example.recipestesting

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipestesting.RecipesAdapter.RecipesViewHolder
import com.example.recipestesting.databinding.RecipeItemBinding
import com.example.recipestesting.models.Hit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class RecipesAdapter(private val recipeList: List<Hit>) :
    RecyclerView.Adapter<RecipesViewHolder>() {
    private var recipes: List<Hit> = recipeList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val binding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        with(holder) {
            with(recipes[position].recipe) {
                binding.recipeName.text = this.label
                /*val imageUrl = this.image
                CoroutineScope(Dispatchers.IO).launch {
                    val stream = withContext(Dispatchers.IO) {
                        URL(imageUrl).openStream()
                    }
                    withContext(Dispatchers.Main){
                        val bitmap = BitmapFactory.decodeStream(stream)
                        binding.recipeImage.setImageBitmap(bitmap)
                    }
                    stream.close()
                }
*/          binding.root.setOnClickListener {
                val context = holder.binding.root.context
                val intent = Intent(context, RecipeDetailActivity::class.java)
                intent.putExtra("RECIPE",recipes[position].recipe)
                context.startActivity(intent)
            }
            }
        }
    }

    fun filterRecipe(filteredRecipes: List<Hit>) {
        recipes = filteredRecipes
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    class RecipesViewHolder(val binding: RecipeItemBinding) : RecyclerView.ViewHolder(
        binding
            .root
    ) {

    }
}