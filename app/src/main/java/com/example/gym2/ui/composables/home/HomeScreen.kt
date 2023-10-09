package com.example.gym2.ui.composables.home

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gym2.viewmodel.UserViewModel
import com.example.gym2.viewmodel.WorkoutViewModel

@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    userViewModel: UserViewModel,
    workoutViewModel: WorkoutViewModel = viewModel(),
    scaffoldState: ScaffoldState
) =  with(workoutViewModel) {

}