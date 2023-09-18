package com.kodeco.recipefinder.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecipeDao {
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun addRecipe(recipe: RecipeDb)

  @Query("SELECT * FROM recipes WHERE id = :id")
  suspend fun findRecipeById(id: Int): RecipeDb

  @Query("SELECT * FROM recipes")
  suspend fun getAllRecipes(): List<RecipeDb>

  @Update
  suspend fun updateRecipeDetails(recipe: RecipeDb)

  @Delete
  suspend fun deleteRecipe(recipe: RecipeDb)

  @Query("DELETE FROM recipes WHERE id = :recipeId")
  suspend fun deleteRecipeById(recipeId: Long)

}