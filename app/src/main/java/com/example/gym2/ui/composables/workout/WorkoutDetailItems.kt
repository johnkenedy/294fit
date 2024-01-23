package com.example.gym2.ui.composables.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Surface
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gym2.R
import com.example.gym2.data.models.ExerciseItem
import com.example.gym2.ui.composables.home.Heading
import com.example.gym2.ui.composables.home.Title
import com.example.gym2.ui.theme.veryDarkBlue
import com.example.gym2.viewmodel.WorkoutViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExerciseItemsDisplay(
    modifier: Modifier = Modifier,
    workoutViewModel: WorkoutViewModel
) {

    val exerciseList =
        workoutViewModel.workoutState.exerciseItems

    LazyColumn(modifier = modifier) {

        exerciseList?.let { exercises ->
            itemsIndexed(
                items = exercises,
                key = { index, item -> item.hashCode() + index + exercises.indexOf(item) }
            ) { index, data ->
                val dismissState = rememberDismissState()

                if (dismissState.targetValue == DismissValue.DismissedToEnd)
                    workoutViewModel.removeExercise(data)
                workoutViewModel.getWorkouts()

                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.StartToEnd),
                    dismissThresholds = { direction ->
                        FractionalThreshold(if (direction == DismissDirection.StartToEnd) 0.25f else 0.5f)
                    },
                    background = {}
                ) {
                    ExerciseItem(data)
                }
            }
        }
    }
}

@Composable
fun ExerciseItem(data: ExerciseItem) {

    val exerciseName by remember { mutableStateOf(data.name) }
    val sets by remember { mutableStateOf(data.sets) }
    val equipment by remember { mutableStateOf(data.equipments) }

    Surface(
        color = veryDarkBlue,
        shape = RectangleShape
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    horizontal = 15.dp,
                    vertical = 20.dp
                )
        ) {

            Column(
                modifier = Modifier
                    .weight(0.95f)
                    .padding(end = 30.dp)
            ) {

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Heading(text = exerciseName.toString().replaceFirstChar { it.uppercase() })
                    Heading(text = sets.toString())
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Title(text = stringResource(id = equipment?.name!!))
                    Title(text = stringResource(id = R.string.sets))
                }
            }

            Column(
                modifier = Modifier
                    .weight(0.1f)
                    .size(50.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                /* TODO: IMPLEMENT EDIT LOGIC
               Icon(
                   imageVector = Icons.Rounded.Edit,
                   contentDescription = stringResource(R.string.edit),
                   tint = holoGreen,
                   modifier = Modifier.size(50.dp)
               )
                */
            }
        }

        Divider(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth(),
            color = Color.Gray.copy(0.1f)
        )
    }
}