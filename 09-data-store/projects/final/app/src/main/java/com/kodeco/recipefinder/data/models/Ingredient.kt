package com.kodeco.recipefinder.data.models

data class Ingredient(
  val id: Int,
  val recipeId: Int?,
  val name: String,
  var aisle: String? = "",
  val image: String? = "",
  val original: String = "",
  val unit: String = "",
  val amount: Double,
)
