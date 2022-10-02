package com.example.recipestesting.models

data class RecipiesResult(
    val _links: Links,
    val count: Int,
    val from: Int,
    val hits: List<Hit>,
    val to: Int
)