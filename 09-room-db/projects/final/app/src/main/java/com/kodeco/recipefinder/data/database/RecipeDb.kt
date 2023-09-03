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