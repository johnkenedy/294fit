package com.example.gym2.data.models

import com.example.gym2.util.DifficultyLevels
import java.time.DayOfWeek

data class WorkoutPlan(
    val name: String? = null,
    val workouts: ArrayList<DayOfWeek>? = null,
    val difficulty: DifficultyLevels.Difficulty? = null,
    val duration: Int? = null
)
