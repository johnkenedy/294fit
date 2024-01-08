package com.example.gym2.ui.composables.home

import android.icu.text.CaseMap.Title
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gym2.R
import com.example.gym2.ui.composables.CalendarDisplay
import com.example.gym2.ui.composables.RegularButton
import com.example.gym2.ui.navigation.Screens
import com.example.gym2.ui.theme.darkBlue
import com.example.gym2.ui.theme.holoGreen
import com.example.gym2.ui.theme.outfit
import com.example.gym2.ui.theme.white
import com.example.gym2.viewmodel.UserViewModel
import com.example.gym2.viewmodel.WorkoutViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    userViewModel: UserViewModel,
    workoutViewModel: WorkoutViewModel = viewModel(),
    scaffoldState: ScaffoldState
) = with(workoutViewModel) {

    getUser(userViewModel.signInState.uid.toString())
    getWorkoutPlan()
    getExercises()

    val scope = rememberCoroutineScope()
    val state = workoutPlanState
    val date = workoutDay
    val selectedDay = calendarSelection
    var openDialog by remember { mutableStateOf(true) }
    var openLogOutDialog by remember { mutableStateOf(false) }

    BackHandler(true) {
        openLogOutDialog = true
    }

    LaunchedEffect(key1 = openDialog) {
        getWorkoutPlan()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.home_screen_background),
                contentScale = ContentScale.Crop
            ),
        color = Color.Transparent
    ) {

        if (openDialog) {
            Dialog(
                onDismissRequest = { openDialog = false },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 15.dp),
                    color = darkBlue,
                    elevation = 20.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {

                        SubHeading(
                            text = stringResource(id = R.string.delete_workout_plan),
                            color = holoGreen
                        )

                        Title(text = stringResource(id = R.string.data_deletion_alert_text))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(
                                20.dp,
                                Alignment.CenterHorizontally
                            )
                        ) {

                            RegularButton(text = stringResource(id = R.string.confirm), onClick = {
                                deleteWorkoutPlan()
                                openDialog
                            })

                            RegularButton(text = stringResource(id = R.string.dismiss), onClick = {
                                openDialog = false
                            })

                        }


                    }

                }

            }
        }
        if (openLogOutDialog) {
            Dialog(onDismissRequest = { openLogOutDialog = false },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 15.dp),
                    color = darkBlue,
                    elevation = 20.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        SubHeading(text = stringResource(id = R.string.logout), color = holoGreen)

                        Title(text = stringResource(id = R.string.logout_alert_text))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(
                                20.dp,
                                Alignment.CenterHorizontally
                            )
                        ) {
                            RegularButton(text = stringResource(id = R.string.confirm), onClick = {
                                userViewModel.logOut()
                                openLogOutDialog = false
                                navController.navigateUp()
                            })

                            RegularButton(text = stringResource(id = R.string.dismiss), onClick = {
                                openLogOutDialog = false
                            })
                        }
                    }
                }

            }
        }

        val snackBarText = stringResource(id = R.string.close)
        state.error?.let {
            if (workoutPlanState.workoutPlan == null) {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        it,
                        snackBarText,
                        SnackbarDuration.Long
                    )
                }
            }
        }

        userViewModel.signInState.uid?.let {
            state.workoutPlan?.let {  workoutPlan ->

                var isWorkoutDay: Boolean by remember { mutableStateOf(false) }
                var isTodayWorkoutDay: Boolean by remember { mutableStateOf(false) }

                workoutPlan.workouts?.let { workouts ->

                    isTodayWorkoutDay =
                        if (selectedDay.dayOfYear == LocalDateTime.now().dayOfYear) {
                            workouts.contains(selectedDay.dayOfWeek)
                        } else false

                    isWorkoutDay = workouts.contains(calendarSelection.dayOfWeek)

                }

                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Heading(
                        text = stringResource(id = R.string.hi) + " " + user?.userName?.replaceFirstChar { it.uppercase() },
                        modifier = Modifier.padding(start = 15.dp)
                    )
                    SubHeading(
                        text = stringResource(id = R.string.your_schedule),
                        modifier = Modifier.padding(start = 15.dp)
                    )
                    CalendarDisplay(
                        modifier = Modifier,
                        workoutPlan.workouts,
                        workoutViewModel = workoutViewModel
                    )
                    Heading(
                        text = date,
                        modifier = Modifier.padding(start = 15.dp)
                    )

                    if (isWorkoutDay) {
                        TrainingCard(
                            modifier = Modifier.padding(horizontal = 15.dp),
                            workoutPlan,
                            onClick = { navController.navigate(route = Screens.WorkoutDetails.route) }
                        )

                        CommandsDisplay(
                            modifier = Modifier.padding(horizontal = 15.dp),
                            iconStart = Icons.Rounded.List,
                            iconCenter = Icons.Rounded.PlayArrow,
                            iconEnd = Icons.Rounded.Delete,
                            iconStartClick = { navController.navigate(route = Screens.WorkoutDetails.route) },
                            iconCenterClick = { navController.navigate(route = Screens.Workout.route) },
                            iconEndClick = { openDialog = true },
                            isWorkoutDay = isTodayWorkoutDay
                        )
                    } else {
                        RestDayView(
                            modifier = Modifier
                                .padding(horizontal = 15.dp)
                                .height(370.dp)
                        )
                    }
                }
            } ?: kotlin.run {
                EmptyWorkoutPlanView(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    user = user?.userName.toString(),
                    onClick = { navController.navigate(Screens.WorkoutPlanSetUp.route) }
                )
            }
        }

    }

}

@Composable
fun Heading(text: String, modifier: Modifier = Modifier, color: Color = white) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        fontFamily = outfit,
        modifier = modifier.paddingFromBaseline(top = 16.dp),
        color = color
    )
}

@Composable
fun SubHeading(text: String, modifier: Modifier = Modifier, color: Color = white) {
    Text(
        text = text,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp,
        fontFamily = outfit,
        modifier = modifier.paddingFromBaseline(top = 16.dp),
        color = color
    )
}

@Composable
fun Title(text: String, modifier: Modifier = Modifier, color: Color = white) {
    Text(
        text = text,
        fontWeight = FontWeight.Light,
        fontFamily = outfit,
        fontSize = 25.sp,
        color = color,
        modifier = modifier
    )
}