package com.example.gym2.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.gym2.ui.theme.darkBlue
import com.example.gym2.viewmodel.UserViewModel
import com.example.gym2.viewmodel.WorkoutViewModel

@Composable
fun RootNavGraph(
    navController: NavHostController,
    userViewModel: UserViewModel = viewModel(),
    workoutViewModel: WorkoutViewModel = viewModel()
) {

    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    val scaffoldState = rememberScaffoldState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        LOGIN_ROUTE -> bottomBarState.value = false
        MAIN_ROUTE -> bottomBarState.value = true
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            when (bottomBarState.value) {
                true -> BottomNavBar(navController = navController, bottomBarState)
                false -> {}
            }
        },
        backgroundColor = darkBlue
    ) {

    }

}