/*
 * Copyright (c) 2023 Kodeco Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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

  suspend fun deleteRecipeById(recipeId: Int) {
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