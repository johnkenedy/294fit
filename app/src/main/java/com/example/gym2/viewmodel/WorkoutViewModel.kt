package com.example.gym2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym2.data.models.ExerciseHistoryItem
import com.example.gym2.data.models.ExerciseVolume
import com.example.gym2.data.models.Muscle
import com.example.gym2.data.models.User
import com.example.gym2.data.models.Workout
import com.example.gym2.data.models.exercises
import com.example.gym2.data.models.states.WorkoutPlanState
import com.example.gym2.domain.WorkoutRepository
import com.example.gym2.util.DifficultyLevels
import com.example.gym2.util.DifficultyLevels.Companion.Beginner
import com.example.gym2.util.getTimeStringFromDouble
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val repository: WorkoutRepository
) : ViewModel() {

    var user: User? by mutableStateOf(User())
        private set

    var userId: String by mutableStateOf("")
        private set

    var workoutPlanState by mutableStateOf(WorkoutPlanState())
        private set

    var workoutId by mutableStateOf("")
        private set

    var workoutDay by mutableStateOf("Today's session")
        private set

    var workoutState by mutableStateOf(Workout())
        private set

    var calendarSelection: LocalDateTime by mutableStateOf(LocalDateTime.now())
        private set

    var muscleGroup = mutableStateListOf<Muscle>()
        private set

    var selectedMuscleGroup by mutableStateOf("")
        private set

    var filteredExerciseList by mutableStateOf(exercises())
        private set

    var userExercisesList by mutableStateOf(exercises())
        private set

    var currentSetItemIndex by mutableIntStateOf(0)
        private set

    var ongoingWorkout by mutableStateOf(Workout())
        private set

    var isWorkoutStarted by mutableStateOf(false)

    var timeElapsed by mutableDoubleStateOf(0.0)

    var timerText by mutableStateOf(getTimeStringFromDouble(timeElapsed))

    var selectedSetItem by mutableStateOf(ExerciseVolume())

    var volumeIndex by mutableIntStateOf(0)

    var selectedDifficulty by mutableStateOf(Beginner)
        private set

    var selectedDays = mutableStateListOf<DayOfWeek>()
        private set

    var currentExerciseId by mutableStateOf("")

    var statsExercises = mutableStateListOf<String>()
        private set

    var chartData = mutableStateListOf<Double>()
    var historyData = mutableStateListOf<ExerciseHistoryItem>()

    var currentExercise by
    if (isWorkoutStarted) mutableStateOf(ongoingWorkout.exerciseItems?.get(0)) else mutableStateOf(
        workoutState.exerciseItems?.get(0)
    )
        private set

    fun addDay(day: DayOfWeek) {
        selectedDays.add(day)
    }

    fun removeDay(day: DayOfWeek) {
        selectedDays.remove(day)
    }

    fun startWorkout() {
        ongoingWorkout = workoutState.copy()
        isWorkoutStarted = true
    }

    fun stopWorkout() {
        isWorkoutStarted = false

        ongoingWorkout.exerciseItems?.forEach {
            val historyItem = ExerciseHistoryItem(
                exercise = it.exercise,
                exerciseVolume = it.volume
            )

            viewModelScope.launch {
                repository.addExerciseHistory(historyItem, userId)
            }
        }
    }

    fun addSetItem() {
        val newItem = currentExercise?.volume?.plus(ExerciseVolume())
        currentExercise = currentExercise?.copy(
            volume = newItem as ArrayList<ExerciseVolume>?
        )
        ongoingWorkout.exerciseItems?.set(currentSetItemIndex, currentExercise!!)
    }

    fun editSetItem(weight: Double, reps: Int) {
        ongoingWorkout.exerciseItems?.let {
            it[currentSetItemIndex].volume?.set(
                volumeIndex, ExerciseVolume(
                    weight = weight,
                    reps = reps
                )
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun removeSetItem(index: Int) {
        val element = currentExercise?.volume?.get(index)
        val newItem = currentExercise?.volume?.minus(element)
        currentExercise = currentExercise?.copy(
            volume = newItem as ArrayList<ExerciseVolume>?
        )
        ongoingWorkout.exerciseItems?.set(currentSetItemIndex, currentExercise!!)
    }

    fun getFirstExercise() {
        val workout = if (isWorkoutStarted) ongoingWorkout else workoutState
        workout.exerciseItems?.let {
            if (it.size > 0)
                currentExercise = it[0]
        }
    }

    fun getNextExercise() {
        val workout = if (isWorkoutStarted) ongoingWorkout else workoutState
        workout.exerciseItems?.let {
            if (currentSetItemIndex < it.size - 1) {
                currentSetItemIndex++
                currentExercise = it[currentSetItemIndex]
            }
        }
    }

    fun getPreviousExercise() {
        val workout = if (isWorkoutStarted) ongoingWorkout else workoutState
        workout.exerciseItems?.let {
            if (currentSetItemIndex > 0) {
                currentSetItemIndex--
                currentExercise = it[currentSetItemIndex]
            }
        }
    }

    fun selectDifficulty(difficulty: DifficultyLevels.Difficulty) {
        selectedDifficulty = difficulty
    }

    fun selectMuscleGroup(targetGroup: List<Muscle>, name: String) {
        selectedMuscleGroup = name
        muscleGroup = targetGroup.toMutableStateList()
    }

    fun selectDay(day: LocalDateTime) {
        calendarSelection = day
    }

    fun getUser(uid: String) {
        userId = uid
        viewModelScope.launch {
            val userData = repository.getUser(uid)
            userData.data?.let {
                user = it.toObject<User>()
            }
        }
    }
}