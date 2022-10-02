package com.example.recipestesting.models

data class Recipe(
    val calories: Double?=null,
    val cautions: List<String>?=null,
    val cuisineType: List<String>?=null,
    val dietLabels: List<String>?=null,
    val digest: List<Digest>?=null,
    val dishType: List<String>?=null,
    val healthLabels: List<String>?=null,
    val image: String,
    val images: Images?=null,
    val ingredientLines: List<String>,
    val ingredients: List<Ingredient>?=null,
    val label: String,
    val mealType: List<String>?=null,
    val shareAs: String?=null,
    val source: String?=null,
    val totalDaily: TotalDaily?=null,
    val totalNutrients: TotalNutrients,
    val totalTime: Int?=null,
    val totalWeight: Double?=null,
    val uri: String?=null,
    val url: String?=null,
    val yield: Int?=null
):java.io.Serializable