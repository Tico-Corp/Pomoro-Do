package com.tico.pomorodo.navigation

import androidx.navigation.NavController

fun NavController.setState(key: String, value: Any) {
    this.currentBackStackEntry?.savedStateHandle?.set(key, value)
}

fun <T> NavController.getState(key: String): T? =
    this.previousBackStackEntry?.savedStateHandle?.get(key)