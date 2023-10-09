package com.example.gym2.data.models.states

import com.example.gym2.data.models.WorkoutPlan

data class WorkoutPlanState(
    val workoutPlan: WorkoutPlan? = null,
    val loading: Boolean = false,
    val error: String? = null
)
