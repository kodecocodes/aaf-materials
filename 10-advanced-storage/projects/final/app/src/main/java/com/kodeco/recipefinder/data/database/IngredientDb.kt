package com.kodeco.recipefinder.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "ingredients")
data class IngredientDb(
  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = "id")
  var id: Int,
  @ColumnInfo(name = "recipeId")
  var recipeId: Int?,
  @ColumnInfo(name = "name")
  var name: String,
  @ColumnInfo(name = "aisle")
  var aisle: String? = "",
  @ColumnInfo(name = "image")
  var image: String? = "",
  @ColumnInfo(name = "original")
  var original: String = "",
  @ColumnInfo(name = "amount")
  var amount: Double = 0.0,
  @ColumnInfo(name = "unit")
  var unit: String = "",
) : Parcelable