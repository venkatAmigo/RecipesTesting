package com.example.recipestesting.models

data class NutrientInfo(
    val label: String,
    val quantity: Double,
    val unit: String,
    val uri:String?=null
):java.io.Serializable
