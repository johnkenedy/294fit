package com.example.gym2.ui.composables.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gym2.ui.navigation.MAIN_ROUTE
import com.example.gym2.ui.theme.veryDarkBlue
import com.example.gym2.R
import com.example.gym2.ui.theme.holoGreen
import com.example.gym2.viewmodel.UserViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    scaffoldState: ScaffoldState
) {

    val state = userViewModel.signInState

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(key1 = state.error) {
        state.error?.let {
            scaffoldState.snackbarHostState.showSnackbar(
                it,
                null,
                SnackbarDuration.Short
            )
        }
    }
    LaunchedEffect(key1 = state.success) {
        if (state.success) {
            navController.navigate(MAIN_ROUTE)
        }
    }

    Surface(
        color = veryDarkBlue.copy(0.7f),
        modifier = Modifier
            .paint(
                painterResource(id = R.drawable.login_background),
                contentScale = ContentScale.Crop
            )
            .fillMaxSize()
    ) {

        if (state.loading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = holoGreen,
                    strokeWidth = 5.dp
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 50.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {

            }
        }

    }
}