package com.example.gym2.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

const val MAIN_ROUTE = "main_route"

sealed class Screens(
    val route: String,
    @StringRes
    val title: Int,
    val icon: ImageVector? = null
) {

}
