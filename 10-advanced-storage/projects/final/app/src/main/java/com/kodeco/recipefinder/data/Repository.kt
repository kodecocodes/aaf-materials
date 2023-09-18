package com.kodeco.recipefinder.data

import com.kodeco.recipefinder.data.database.IngredientDao
import com.kodeco.recipefinder.data.database.IngredientDb
import com.kodeco.recipefinder.data.database.RecipeDao
import com.kodeco.recipefinder.data.database.RecipeDatabase
import com.kodeco.recipefinder.data.database.RecipeDb

class Repository(recipeDatabase: RecipeDatabase) {
  private val recipeDao: RecipeDao = recipeDatabase.recipeDao()
  private val ingredientDao: IngredientDao = recipeDatabase.ingredientDao()

  suspend fun findAllRecipes(): List<RecipeDb> {
    return recipeDao.getAllRecipes()
  }

  suspend fun findBookmarkById(id: Int): RecipeDb {
    return recipeDao.findRecipeById(id)
  }

  suspend fun findRecipeById(id: Int): RecipeDb {
    return recipeDao.findRecipeById(id)
  }

  suspend fun findAllIngredients(): List<IngredientDb> {
    return ingredientDao.getAllIngredients()
  }

  suspend fun findRecipeIngredients(recipeId: Int): List<IngredientDb> {
    return ingredientDao.findIngredientsByRecipe(recipeId)
  }

  suspend fun insertRecipe(recipe: RecipeDb) {
    recipeDao.addRecipe(recipe)
  }

  suspend fun insertIngredients(ingredients: List<IngredientDb>) {
    ingredients.forEach {
      ingredientDao.addIngredient(it)
    }
  }

  suspend fun deleteRecipe(recipe: RecipeDb) {
    recipeDao.deleteRecipe(recipe)
  }

  suspend fun deleteRecipeById(recipeId: Long) {
    recipeDao.deleteRecipeById(recipeId)
  }

  suspend fun deleteIngredient(ingredient: IngredientDb) {
    ingredientDao.deleteIngredient(ingredient)
  }

  suspend fun deleteIngredients(ingredients: List<IngredientDb>) {
    ingredients.forEach {
      ingredientDao.deleteIngredient(it)
    }
  }

  suspend fun deleteRecipeIngredients(recipeId: Int) {
    val ingredients = findRecipeIngredients(recipeId)
    ingredients.forEach {
      ingredientDao.deleteIngredient(it)
    }
  }
}