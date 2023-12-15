package com.example.gym2.domain

import com.example.gym2.data.models.ExerciseHistoryItem
import com.example.gym2.data.models.Workout
import com.example.gym2.data.models.WorkoutPlan
import com.example.gym2.util.Resource
import com.google.firebase.firestore.DocumentSnapshot

interface WorkoutRepository {

    suspend fun addWorkoutPlan(
        workoutPlan: WorkoutPlan,
        workouts: ArrayList<Workout>,
        uid: String
    ): Resource<Void>

    suspend fun getUser(uid: String): Resource<DocumentSnapshot>
    suspend fun addExerciseHistory(exercise: ExerciseHistoryItem, uid: String)

}