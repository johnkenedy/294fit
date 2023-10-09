package com.example.gym2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gym2.data.models.User
import com.example.gym2.data.models.states.WorkoutPlanState
import com.example.gym2.domain.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

}