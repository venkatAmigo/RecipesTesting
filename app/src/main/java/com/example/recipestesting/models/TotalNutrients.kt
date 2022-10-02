package com.example.recipestesting.models

import com.google.gson.annotations.SerializedName


data class TotalNutrients(
    val ENERC_KCAL:NutrientInfo,
    val FAT: NutrientInfo,
    val SUGAR: NutrientInfo,
):java.io.Serializable