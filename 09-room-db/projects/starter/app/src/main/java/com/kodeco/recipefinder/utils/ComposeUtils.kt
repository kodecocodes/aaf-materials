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

package com.kodeco.recipefinder.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * The first 2 methods are taken from https://medium.com/dev-genius/a-few-shorthands-for-jetpack-compose-b5e75c37a381
 * by Matt Robertson.
 */

/**
 * Shorthand for
 *
 * ```
 * remember {
 *     mutableStateOf(...)
 * }
 * ```
 */
@Composable
inline fun <T> rememberState(crossinline producer: @DisallowComposableCalls () -> T) =
  remember { mutableStateOf(producer()) }

/**
 * Shorthand for
 *
 * ```
 * remember(key) {
 *     mutableStateOf(...)
 * }
 * ```
 */
@Composable
inline fun <T> rememberState(key: Any?, crossinline producer: @DisallowComposableCalls () -> T) =
  remember(key) { mutableStateOf(producer()) }

/**
 * Provide a way to pass variables to view models
 */
inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
  object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = f() as T
  }