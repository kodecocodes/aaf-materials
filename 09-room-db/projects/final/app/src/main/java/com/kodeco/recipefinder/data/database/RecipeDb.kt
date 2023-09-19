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

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "recipes")
data class RecipeDb(
  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = "id")
  var id: Int,
  @ColumnInfo(name = "title")
  var title: String,
  @ColumnInfo(name = "image")
  var image: String?,
  @ColumnInfo(name = "summary")
  var summary: String = "",
  @ColumnInfo(name = "instructions")
  var instructions: String? = "",
  @ColumnInfo(name = "sourceUrl")
  var sourceUrl: String = "",
  @ColumnInfo(name = "preparationMinutes")
  var preparationMinutes: Int = 0,
  @ColumnInfo(name = "cookingMinutes")
  var cookingMinutes: Int = 0,
  @ColumnInfo(name = "readyInMinutes")
  var readyInMinutes: Int = 0,
  @ColumnInfo(name = "servings")
  var servings: Int = 0,
) : Parcelable