package com.kodeco.recipefinder.network

import com.kodeco.recipefinder.viewmodels.PAGE_SIZE
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val apiKey = "<Your Key Here>"

interface SpoonacularService {
  @GET("recipes/complexSearch?&apiKey=$apiKey")
  suspend fun queryRecipes(
      @Query("query") query: String,
      @Query("offset") offset: Int,
      @Query("number") number: Int = PAGE_SIZE
  ): RecipeResponse

  @GET("recipes/{id}/information?includeNutrition=false&apiKey=$apiKey")
  suspend fun queryRecipe(@Path("id") id: Int): SpoonacularRecipe
}

object RetrofitInstance {
  private const val BASE_URL = "https://api.spoonacular.com/"

  private fun provideMoshi(): Moshi =
      Moshi
          .Builder()
          .add(KotlinJsonAdapterFactory())
          .build()

  private val retrofit: Retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
        .build()
  }

  val spoonacularService: SpoonacularService by lazy {
    retrofit.create(SpoonacularService::class.java)
  }
}
