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