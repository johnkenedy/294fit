package com.example.gym2.ui.composables.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.gym2.R
import com.example.gym2.data.models.equipments
import com.example.gym2.ui.composables.FloatingAddButton
import com.example.gym2.ui.composables.RegularButton
import com.example.gym2.ui.composables.home.Heading
import com.example.gym2.ui.composables.home.SubHeading
import com.example.gym2.ui.composables.home.Title
import com.example.gym2.ui.composables.home.WorkoutInfo
import com.example.gym2.ui.theme.darkBlue
import com.example.gym2.ui.theme.holoGreen
import com.example.gym2.ui.theme.lightBlue
import com.example.gym2.util.DifficultyLevels.Companion.Intermediate
import com.example.gym2.viewmodel.WorkoutViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WorkoutDetailScreen(
    navController: NavHostController = rememberNavController(),
    workoutViewModel: WorkoutViewModel = viewModel()
) = with(workoutViewModel) {

    getWorkouts()

    val state = workoutState
    val workoutPlan = workoutPlanState.workoutPlan
    var openDialog by remember { mutableStateOf(false) }
    var exBoxExpanded by remember { mutableStateOf(false) }
    var selectedExercise by remember { mutableStateOf(userExercisesList[0]) }
    val equipments = equipments().toSet().toList()
    var eqBoxExpanded by remember { mutableStateOf(false) }
    var selectedEquipment by remember { mutableStateOf(equipments[0]) }
    var setAmount by remember { mutableIntStateOf(1) }

    if (openDialog) {
        Dialog(
            onDismissRequest = { openDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {

            Surface(
                modifier = Modifier
                    .width(300.dp),
                color = lightBlue,
                shape = RoundedCornerShape(40.dp)
            ) {

                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(30.dp)
                ) {
                    SubHeading(
                        text = stringResource(id = R.string.add_exercise_heading),
                        color = holoGreen
                    )

                    ExposedDropdownMenuBox(
                        expanded = exBoxExpanded,
                        onExpandedChange = {
                            exBoxExpanded = !exBoxExpanded
                        }
                    ) {
                        TextField(
                            readOnly = true,
                            value = selectedExercise.name.toString(),
                            onValueChange = { value ->
                                selectedExercise = userExercisesList.first() { it.name == value }
                            },
                            label = {
                                Text(
                                    text = stringResource(id = R.string.exercise),
                                    color = holoGreen
                                )
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = exBoxExpanded
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                trailingIconColor = holoGreen,
                                focusedTrailingIconColor = holoGreen,
                                disabledTrailingIconColor = holoGreen
                            ),
                            textStyle = TextStyle(fontSize = 20.sp)
                        )
                        ExposedDropdownMenu(
                            expanded = exBoxExpanded,
                            onDismissRequest = { exBoxExpanded = false }
                        ) {
                            userExercisesList.forEach { selectionOption ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedExercise = selectionOption
                                        exBoxExpanded = false
                                    }
                                ) {
                                    Text(text = selectionOption.name!!)
                                }
                            }
                        }
                    }

                    ExposedDropdownMenuBox(
                        expanded = eqBoxExpanded,
                        onExpandedChange = {
                            eqBoxExpanded = !eqBoxExpanded
                        }
                    ) {
                        TextField(
                            readOnly = true,
                            value = stringResource(id = selectedEquipment.name!!),
                            onValueChange = { },
                            label = {
                                Text(
                                    text = stringResource(id = R.string.equipment),
                                    color = holoGreen
                                )
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = eqBoxExpanded
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                trailingIconColor = holoGreen,
                                focusedTrailingIconColor = holoGreen,
                                disabledTrailingIconColor = holoGreen
                            ),
                            textStyle = TextStyle(fontSize = 20.sp)
                        )
                        ExposedDropdownMenu(
                            expanded = eqBoxExpanded,
                            onDismissRequest = { eqBoxExpanded = false }
                        ) {
                            equipments.forEach { selectionOption ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedEquipment = selectionOption
                                        eqBoxExpanded = false
                                    }
                                ) {
                                    Text(text = stringResource(id = selectionOption.name!!))
                                }
                            }
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        SubHeading(text = stringResource(id = R.string.sets), modifier = Modifier)

                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(
                                20.dp,
                                Alignment.CenterHorizontally
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            IconButton(onClick = {
                                if (setAmount > 0) {
                                    setAmount--
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_remove),
                                    contentDescription = null,
                                    tint = holoGreen,
                                    modifier = Modifier
                                        .size(25.dp)
                                )
                            }

                            Title(text = setAmount.toString())

                            IconButton(
                                onClick = { setAmount++ }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.AddCircle,
                                    contentDescription = null,
                                    tint = holoGreen,
                                    modifier = Modifier
                                        .size(25.dp)
                                )
                            }
                        }
                    }

                    RegularButton(
                        text = stringResource(id = R.string.add),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            openDialog = false
                            addExerciseToWorkout(
                                exerciseName = selectedExercise.name.toString(),
                                equipments = selectedEquipment,
                                sets = setAmount
                            )
                            getWorkouts()
                        }
                    )
                }
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = darkBlue
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Heading(
                text = workoutPlan?.name?.replaceFirstChar { it.uppercase() }.toString(),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
            WorkoutInfo(
                duration = workoutPlan?.duration.toString(),
                difficulty = Intermediate,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
            )
            ExerciseItemsDisplay(
                modifier = Modifier
                    .height(500.dp),
                workoutViewModel = workoutViewModel
            )

            FloatingAddButton(Modifier.align(Alignment.CenterHorizontally), onClick = {
                openDialog = true
            })
        }
    }
}