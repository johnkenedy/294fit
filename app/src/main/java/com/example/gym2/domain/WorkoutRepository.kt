package com.example.gym2.domain

import com.example.gym2.data.models.Workout
import com.example.gym2.data.models.WorkoutPlan
import com.example.gym2.util.Resource

interface WorkoutRepository {

    suspend fun addWorkoutPlan(
        workoutPlan: WorkoutPlan,
        workouts: ArrayList<Workout>,
        uid: String
    ): Resource<Void>

}