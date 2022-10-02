package com.example.recipestesting.utils

import com.example.recipestesting.models.RecipiesResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("recipes/v2?type=public")
    suspend fun getRecipes(@Query("q") query:String,@Query("app_id") appId:String,@Query
        ("app_key") appKey:String):RecipiesResult

    companion object{
        val BASE_URL= "https://api.edamam.com/api/"
        fun getInstance():ApiInterface{
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}