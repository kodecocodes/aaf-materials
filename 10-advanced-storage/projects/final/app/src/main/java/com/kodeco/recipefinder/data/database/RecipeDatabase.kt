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
package com.kodeco.recipefinder.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import kotlin.random.Random

@Database(entities = [RecipeDb::class, IngredientDb::class], version = 1, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {

  abstract fun recipeDao(): RecipeDao
  abstract fun ingredientDao(): IngredientDao

  companion object {
    const val PASSCODE_KEY = "PASSCODE_KEY"
    /*The value of a volatile variable will never be cached, and all writes and reads will be done to and from the main memory.
    This helps make sure the value of INSTANCE is always up-to-date and the same for all execution threads.
    It means that changes made by one thread to INSTANCE are visible to all other threads immediately.*/
    @Volatile
    private var INSTANCE: RecipeDatabase? = null

    fun getPassCode(stringSize: Int): String {
      val randomString = StringBuilder()
      val random = Random.Default
      for (i in 0 until stringSize) {
        randomString.append('a' + random.nextInt(26))
      }
      return randomString.toString()
    }

    fun getInstance(context: Context, passCode: CharArray): RecipeDatabase {
      // only one thread of execution at a time can enter this block of code
      synchronized(this) {
        var instance = INSTANCE

        if (instance == null) {
          val supportFactory = SupportFactory(SQLiteDatabase.getBytes(passCode))
          instance = Room.databaseBuilder(
            context.applicationContext,
            RecipeDatabase::class.java,
            "recipe_database"
          )
            .openHelperFactory(supportFactory)
            .fallbackToDestructiveMigration()
            .build()

          INSTANCE = instance
        }
        return instance
      }
    }
  }
}