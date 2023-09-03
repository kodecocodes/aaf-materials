package com.kodeco.recipefinder.network

import com.kodeco.recipefinder.data.models.Recipe
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipeResponse(
  val offset: Int,
  val number: Int,
  val totalResults: Int,
  @Json(name = "results")
  val recipes: List<Recipe>
)
