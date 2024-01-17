package com.example.gym2.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gym2.ui.theme.darkBlue
import com.example.gym2.ui.theme.holoGreen

@Preview
@Composable
fun CommandsDisplay(
    modifier: Modifier = Modifier,
    iconStart: ImageVector = Icons.Rounded.ArrowBack,
    iconStartClick: () -> Unit = {},
    iconCenter: ImageVector = Icons.Rounded.PlayArrow,
    iconCenterClick: () -> Unit = {},
    iconEnd: ImageVector = Icons.Rounded.ArrowForward,
    iconEndClick: () -> Unit = {},
    isWorkoutDay: Boolean = true
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Surface(
            shape = CircleShape,
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .align(Alignment.Top)
                .clickable { iconStartClick() },
            elevation = 4.dp,
            color = holoGreen
        ) {
            Icon(
                imageVector = iconStart,
                contentDescription = null,
                tint = darkBlue,
                modifier = Modifier.padding(15.dp)
            )
        }

        if (isWorkoutDay) {
            Surface(
                shape = CircleShape,
                modifier = Modifier
                    .width(95.dp)
                    .height(95.dp)
                    .clickable { iconCenterClick() },
                elevation = 4.dp,
                color = holoGreen
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Icon(
                        imageVector = iconCenter,
                        contentDescription = null,
                        tint = darkBlue,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        } else { Spacer(modifier = Modifier.size(95.dp)) }

        Surface(
            shape = CircleShape,
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .align(Alignment.Top)
                .clickable { iconEndClick() },
            elevation = 4.dp,
            color = holoGreen
        ) {

            Icon(
                imageVector = iconEnd,
                contentDescription = null,
                tint = darkBlue,
                modifier = Modifier.padding(15.dp)
            )
        }
    }
}