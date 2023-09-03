package com.kodeco.recipefinder.data


import com.kodeco.recipefinder.data.database.IngredientDb
import com.kodeco.recipefinder.data.database.RecipeDb
import com.kodeco.recipefinder.data.models.Ingredient
import com.kodeco.recipefinder.data.models.Recipe
import com.kodeco.recipefinder.network.ExtendedIngredient
import com.kodeco.recipefinder.network.SpoonacularRecipe

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
): SpoonacularRecipe {
  return SpoonacularRecipe(
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

fun spoonacularRecipeToRecipe(recipe: SpoonacularRecipe): RecipeDb {
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

