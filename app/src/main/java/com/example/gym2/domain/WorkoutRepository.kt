package com.example.gym2.domain

import androidx.lifecycle.MutableLiveData
import com.example.gym2.data.models.Exercise
import com.example.gym2.data.models.ExerciseHistoryItem
import com.example.gym2.data.models.ExerciseItem
import com.example.gym2.data.models.ExerciseVolume
import com.example.gym2.data.models.Workout
import com.example.gym2.data.models.WorkoutPlan
import com.example.gym2.util.Resource
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface WorkoutRepository {

    suspend fun addWorkoutPlan(
        workoutPlan: WorkoutPlan,
        workouts: ArrayList<Workout>,
        uid: String
    ): Resource<Void>

    suspend fun getWorkoutPlan(uid: String): Resource<QuerySnapshot>
    suspend fun addWorkouts(uid: String): Resource<Void>
    suspend fun getWorkouts(uid: String): Resource<QuerySnapshot>
    suspend fun getExercises(uid: String): MutableLiveData<Resource<QuerySnapshot>>
    suspend fun getUser(uid: String): Resource<DocumentSnapshot>
    suspend fun addNewExercise(exercise: Exercise, uid: String): Resource<Void>
    suspend fun addExerciseHistory(exercise: ExerciseHistoryItem, uid: String)
    suspend fun updateWorkout(
        workoutId: String,
        exerciseItem: ExerciseItem? = null,
        volume: ExerciseVolume? = null,
        workout: Workout,
        uid: String
    ): Resource<Void>
    suspend fun getHistoryData(uid: String): Resource<QuerySnapshot>
    suspend fun getHistoryDataDetails(exerciseId: String, uid: String): Resource<QuerySnapshot>
    suspend fun deleteWorkoutPlan(workoutPlanId: String, uid: String)

}