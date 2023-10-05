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


import com.kodeco.recipefinder.data.database.IngredientDb
import com.kodeco.recipefinder.data.database.RecipeDb
import com.kodeco.recipefinder.data.models.ExtendedIngredient
import com.kodeco.recipefinder.data.models.Ingredient
import com.kodeco.recipefinder.data.models.Recipe
import com.kodeco.recipefinder.data.models.RecipeInformationResponse

fun recipeToDb(recipe: Recipe): RecipeDb {
  return RecipeDb(id = recipe.id, title = recipe.title, image = recipe.image)
}

fun recipeDbsToRecipes(recipes: List<RecipeDb>): List<Recipe> {
  val modelRecipes = mutableListOf<Recipe>()
  recipes.forEach {
    modelRecipes.add(recipeDbToRecipe(it))
  }
  return modelRecipes
}

fun recipeDbToRecipe(recipe: RecipeDb): Recipe {
  return Recipe(id = recipe.id, title = recipe.title, image = recipe.image)
}

fun recipeToSpoonacularRecipe(
  recipe: RecipeDb, ingredients: List<ExtendedIngredient>
): RecipeInformationResponse {
  return RecipeInformationResponse(
    id = recipe.id,
    title = recipe.title,
    image = recipe.image,
    extendedIngredients = ingredients,
    preparationMinutes = recipe.preparationMinutes,
    cookingMinutes = recipe.cookingMinutes,
    servings = recipe.servings,
    summary = recipe.summary,
    instructions = recipe.instructions,
    sourceUrl = recipe.sourceUrl,
    readyInMinutes = recipe.readyInMinutes,
  )
}

fun spoonacularRecipeToRecipe(recipe: RecipeInformationResponse): RecipeDb {
  return RecipeDb(
    id = recipe.id,
    title = recipe.title,
    image = recipe.image,
    summary = recipe.summary,
    instructions = recipe.instructions,
    sourceUrl = recipe.sourceUrl,
    preparationMinutes = recipe.preparationMinutes,
    cookingMinutes = recipe.cookingMinutes,
    readyInMinutes = recipe.readyInMinutes,
    servings = recipe.servings
  )
}

fun spoonacularIngredientsToIngredients(
  recipeId: Int, ingredients: List<ExtendedIngredient>
): List<IngredientDb> {
  val modelIngredients = mutableListOf<IngredientDb>()
  ingredients.forEach {
    modelIngredients.add(spoonacularIngredientToIngredientDb(recipeId, it))
  }
  return modelIngredients
}

fun ingredientToSpoonacular(ingredient: IngredientDb): ExtendedIngredient {
  return ExtendedIngredient(
    id = ingredient.id,
    name = ingredient.name,
    amount = ingredient.amount,
    aisle = ingredient.aisle,
    image = ingredient.image,
    original = ingredient.original,
    unit = ingredient.unit
  )
}

fun ingredientDbsToSpoonacular(ingredients: List<IngredientDb>): List<ExtendedIngredient> {
  val modelIngredients = mutableListOf<ExtendedIngredient>()
  ingredients.forEach {
    modelIngredients.add(ingredientToSpoonacular(it))
  }
  return modelIngredients
}

fun ingredientDbsToIngredients(ingredients: List<IngredientDb>): List<Ingredient> {
  val modelIngredients = mutableListOf<Ingredient>()
  ingredients.forEach {
    modelIngredients.add(ingredientDbToIngredient(it))
  }
  return modelIngredients
}

fun ingredientDbToIngredient(ingredient: IngredientDb): Ingredient {
  return Ingredient(
    id = ingredient.id,
    recipeId = ingredient.recipeId,
    name = ingredient.name,
    aisle = ingredient.aisle,
    image = ingredient.image,
    unit = ingredient.unit,
    original = ingredient.original,
    amount = ingredient.amount
  )
}

fun spoonacularIngredientToIngredientDb(
  recipeId: Int, ingredient: ExtendedIngredient
): IngredientDb {
  return IngredientDb(
    id = ingredient.id,
    recipeId = recipeId,
    name = ingredient.name,
    aisle = ingredient.aisle,
    image = ingredient.image,
    unit = ingredient.unit,
    original = ingredient.original,
    amount = ingredient.amount
  )
}

