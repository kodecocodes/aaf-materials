package com.kodeco.recipefinder.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface IngredientDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun addIngredient(ingredientDb: IngredientDb)

  @Query("SELECT * FROM ingredients WHERE id = :id")
  fun findIngredientById(id: Int): IngredientDb

  @Query("SELECT * FROM ingredients WHERE recipeId = :id")
  fun findIngredientsByRecipe(id: Int): List<IngredientDb>

  @Query("SELECT * FROM ingredients")
  fun getAllIngredients(): List<IngredientDb>

  @Update
  suspend fun updateIngredientDetails(ingredientDb: IngredientDb)

  @Delete
  suspend fun deleteIngredient(ingredientDb: IngredientDb)
}