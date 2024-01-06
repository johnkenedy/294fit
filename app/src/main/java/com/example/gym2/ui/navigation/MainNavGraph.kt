package com.example.gym2.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.gym2.ui.composables.home.HomeScreen
import com.example.gym2.ui.composables.stats.StatsScreen
import com.example.gym2.ui.theme.holoGreen
import com.example.gym2.ui.theme.veryDarkBlue
import com.example.gym2.viewmodel.UserViewModel
import com.example.gym2.viewmodel.WorkoutViewModel

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    userViewModel: UserViewModel,
    workoutViewModel: WorkoutViewModel,
    scaffoldState: ScaffoldState
) {
    navigation(startDestination = Screens.Home.route, route = MAIN_ROUTE) {

        composable(route = Screens.Home.route) {
            HomeScreen(navController, userViewModel, workoutViewModel, scaffoldState)
            bottomBarState.value = true
        }

        composable(route = Screens.Stats.route) {
            StatsScreen(navController, workoutViewModel)
            bottomBarState.value = true
        }

    }
}

@Composable
fun BottomNavBar(
    navController: NavHostController = rememberNavController(),
    bottomBarState: MutableState<Boolean>
) {
    val screens = listOf(
        Screens.Home,
        Screens.Stats,
        Screens.Profile,
        Screens.Exercises
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    AnimatedVisibility(
        visible = bottomBarState.value, enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        BottomNavigation(
            modifier = Modifier
                .graphicsLayer {
                    clip = true
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                }
                .height(70.dp),
            elevation = 10.dp,
            backgroundColor = holoGreen

        ) {

            screens.forEach {

                this@BottomNavigation.AddItem(
                    screens = it,
                    currentDestination = currentDestination,
                    navController = navController
                )

            }

        }
    }
}

@Composable
fun RowScope.AddItem(
    screens: Screens,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    BottomNavigationItem(
        label = {
            Text(text = stringResource(id = screens.title))
        },

        onClick = {
            navController.navigate(screens.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        icon = { Icon(imageVector = screens.icon!!, contentDescription = null) },
        selected = currentDestination?.hierarchy?.any { it.route == screens.route } == true,
        selectedContentColor = veryDarkBlue,
        unselectedContentColor = veryDarkBlue.copy(ContentAlpha.disabled)
    )

}