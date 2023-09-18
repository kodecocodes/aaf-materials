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

package com.kodeco.recipefinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.kodeco.recipefinder.data.Prefs
import com.kodeco.recipefinder.data.Repository
import com.kodeco.recipefinder.data.database.RecipeDatabase
import com.kodeco.recipefinder.ui.MainScreen
import com.kodeco.recipefinder.ui.RecipeDetails
import com.kodeco.recipefinder.ui.theme.RecipeFinderTheme

val LocalNavigatorProvider =
  compositionLocalOf<NavHostController> { error("No navigation provided") }

val LocalRepositoryProvider =
  compositionLocalOf<Repository> { error("No repository provided") }

val LocalPrefsProvider =
  compositionLocalOf<Prefs> { error("No prefs provided") }

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      RecipeFinderTheme(darkTheme = false) {
        // A surface container using the 'background' color from the theme
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          val context = LocalContext.current
          val navController = rememberNavController()
          val repository = remember {
            Repository(
              Room.databaseBuilder(
                context,
                RecipeDatabase::class.java,
                "Recipes"
              ).build()
            )
          }
          val prefs = remember { Prefs(context) }
          CompositionLocalProvider(
            LocalNavigatorProvider provides navController,
            LocalPrefsProvider provides prefs,
            LocalRepositoryProvider provides repository,
          ) {
            NavHost(navController = navController, startDestination = "main") {
              composable("main") { MainScreen() }
              composable(
                "details/{recipeId}",
                arguments = listOf(navArgument("recipeId") {
                  type = NavType.IntType
                })
              ) { backStackEntry ->
                RecipeDetails(
                  recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
                )
              }
              composable(
                "bookmarks/{recipeId}",
                arguments = listOf(navArgument("recipeId") {
                  type = NavType.IntType
                })
              ) { backStackEntry ->
                RecipeDetails(
                  databaseRecipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
                )
              }
            }
          }
        }
      }
    }
  }
}


@Preview(showBackground = true)
@Composable
fun MainPreview() {
  RecipeFinderTheme {
    MainScreen()
  }
}