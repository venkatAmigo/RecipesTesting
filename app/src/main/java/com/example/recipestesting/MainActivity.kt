package com.example.recipestesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipestesting.databinding.ActivityMainBinding
import com.example.recipestesting.databinding.RecipeItemBinding
import com.example.recipestesting.models.Hit
import com.example.recipestesting.utils.ApiInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var recipesAdapter: RecipesAdapter
    lateinit var binding: ActivityMainBinding
    val APP_ID ="ad60051a"
    val APP_KEY ="f97011c2e6970989e9adb9e273f25ebb"
    lateinit var recipeList: List<Hit>
    lateinit var recipeRecyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recipeRecyclerView = binding.recipesRecycler
        setupRecyclerView()
        getData()
        binding.searchView.isIconifiedByDefault= false
        binding.searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterData(query)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
               return false
            }

        })
    }

    private fun filterData(query: String?) {
        val filteredList: MutableList<Hit> = mutableListOf()
        recipeList.forEach {
            if (query != null) {
                if(it.recipe.label.lowercase().contains(query.lowercase())){
                    filteredList.add(it)
                }
            }
        }
        recipesAdapter.filterRecipe(filteredList)
    }

    private fun getData() {
        CoroutineScope(Dispatchers.IO).launch{
            val recipeResult = ApiInterface.getInstance().getRecipes("chicken",APP_ID,APP_KEY)
            recipeList = recipeResult.hits
            recipesAdapter = RecipesAdapter(recipeList)
            withContext(Dispatchers.Main){
                recipeRecyclerView.adapter = recipesAdapter
            }
        }
    }

    private fun setupRecyclerView() {
        recipeRecyclerView.layoutManager = GridLayoutManager(this,3,RecyclerView.VERTICAL,
            false)
    }
}