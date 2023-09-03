package com.kodeco.recipefinder.network

data class SpoonacularRecipe(
    val id: Int = 0,
    val title: String = "",
    val image: String? = "",
    val imageType: String = "",
    val summary: String = "",
    val instructions: String? = "",
    val sourceUrl: String = "",

    val preparationMinutes: Int = 0,
    val cookingMinutes: Int = 0,
    val extendedIngredients: List<ExtendedIngredient> = listOf(),
    val readyInMinutes: Int = 0,
    val servings: Int = 0,

    )

data class ExtendedIngredient(
    val id: Int = 0,
    val name: String = "",
    val aisle: String? = "",
    val image: String? = "",
    val original: String = "",
    val amount: Double = 0.0,
    val unit: String = "",
)
