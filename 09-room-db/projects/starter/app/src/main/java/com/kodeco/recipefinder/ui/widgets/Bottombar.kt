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

package com.kodeco.recipefinder.ui.widgets

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.kodeco.recipefinder.ui.theme.RecipeFinderTheme

@Composable
fun BottomBar(bottomItems: List<BottomItem>) {
  BottomNavigation(
    backgroundColor = MaterialTheme.colorScheme.primary,
  ) {

    bottomItems.forEach { item ->
      BottomNavigationItem(
        selected = item.selected,
        onClick = item.onclick,
        label = {
          Text(
            text = item.icon.contextText, style = TextStyle(
              color = MaterialTheme.colorScheme.onPrimary,
              fontSize = 12.sp
            )
          )
        },
        icon = {
          Icon(
            item.icon.icon,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = item.icon.contextText
          )
        })

    }
  }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  RecipeFinderTheme {
    BottomBar(
      listOf(
        BottomItem(icon = IconInfo(icon = Icons.Filled.Home, contextText = "Home")),
        BottomItem(icon = IconInfo(icon = Icons.Filled.AccountCircle, contextText = "Account"))
      )
    )
  }
}