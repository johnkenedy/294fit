package com.example.gym2.ui.composables.workout

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gym2.R
import com.example.gym2.data.models.ExerciseItem
import com.example.gym2.ui.composables.FloatingAddButton
import com.example.gym2.ui.composables.RoundedCheckBox
import com.example.gym2.ui.composables.home.Heading
import com.example.gym2.ui.composables.home.SubHeading
import com.example.gym2.ui.composables.home.Title
import com.example.gym2.ui.theme.darkBlue
import com.example.gym2.ui.theme.holoGreen
import com.example.gym2.ui.theme.holoRed
import com.example.gym2.ui.theme.veryDarkBlue
import com.example.gym2.ui.theme.white
import com.example.gym2.viewmodel.WorkoutViewModel

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun WorkoutCard(
    exercise: ExerciseItem,
    modifier: Modifier = Modifier,
    workoutViewModel: WorkoutViewModel,
    onRemoveSet: (Int) -> Unit,
    onAddSet: () -> Unit,
    onItemClick: () -> Unit
) {

    val sets = exercise.sets
    val equipment = exercise.equipments

    AnimatedContent(
        targetState = exercise.name,
        transitionSpec = { (fadeIn() + scaleIn()).togetherWith(fadeOut() + scaleOut()) },
        label = ""
    ) {
        Box(
            modifier = modifier
                .width(370.dp)
                .height(520.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(veryDarkBlue)
        ) {
            Surface(
                color = veryDarkBlue.copy(0.6f),
                modifier = Modifier
                    .paint(
                        painter = painterResource(id = R.drawable.workout_card_background),
                        contentScale = ContentScale.Crop
                    )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Heading(text = exercise.name.toString(), modifier = Modifier)
                        Heading(text = sets.toString(), modifier = Modifier)
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Title(text = stringResource(id = equipment?.name!!))
                        Title(text = stringResource(id = R.string.sets))
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp),
                        color = holoGreen.copy(0.5f)
                    )

                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 80.dp)
                    ) {
                        Title(text = stringResource(id = R.string.kg))
                        Title(text = stringResource(id = R.string.reps))
                    }

                    workoutViewModel.currentExercise?.let {
                        LazyColumn(
                            content = {
                                it.volume?.let { list ->

                                    itemsIndexed(list) { index, data ->
                                        SetItem(
                                            set = data.set!!,
                                            reps = data.reps!!,
                                            weight = data.weight!!,
                                            modifier = Modifier,
                                            onRemoveClick = { onRemoveSet(index) },
                                            onClick = {
                                                workoutViewModel.volumeIndex = index
                                                onItemClick()
                                                workoutViewModel.selectedSetItem = data
                                            }
                                        )
                                    }
                                }
                            },
                            modifier = Modifier.height(300.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    FloatingAddButton(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        onClick = onAddSet
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SetItem(
    modifier: Modifier = Modifier,
    set: Int = 1,
    reps: Int = 12,
    weight: Double = 80.00,
    onRemoveClick: () -> Unit = {},
    onClick: () -> Unit = {}
) {

    var checkedState by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 15.dp)
            .clickable { onClick() },
        color = darkBlue,
        elevation = 20.dp
    ) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            RoundedCheckBox(onCheckedChange = { checkedState = it })

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SubHeading(text = weight.toString())
                Spacer(modifier = Modifier.width(15.dp))
                Icon(imageVector = Icons.Rounded.Close, contentDescription = null, tint = white)
                Spacer(modifier = Modifier.width(15.dp))
                SubHeading(text = reps.toString())
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_remove),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clickable { onRemoveClick() },
                tint = holoRed
            )
        }
    }
}