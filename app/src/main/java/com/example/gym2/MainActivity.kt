package com.example.gym2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.gym2.data.service.WorkoutTimerService
import com.example.gym2.ui.navigation.RootNavGraph
import com.example.gym2.ui.theme.Gym2Theme
import com.example.gym2.util.getTimeStringFromDouble
import com.example.gym2.viewmodel.UserViewModel
import com.example.gym2.viewmodel.WorkoutViewModel

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    private val workoutViewModel: WorkoutViewModel by viewModels()
    private var timeElapsed = 0.0
    private lateinit var serviceIntent: Intent

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent) {
            timeElapsed = intent.getDoubleExtra(WorkoutTimerService.TIME_ELAPSED, 0.0)
            workoutViewModel.timerText = getTimeStringFromDouble(timeElapsed)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        serviceIntent = Intent(this, WorkoutTimerService::class.java)
        registerReceiver(
            updateTime,
            IntentFilter(WorkoutTimerService.TIMER_UPDATED)
        )
        setContent {
            Gym2Theme {
                val workoutViewModel = hiltViewModel<WorkoutViewModel>()
                val userViewModel = hiltViewModel<UserViewModel>()
                navController = rememberNavController()
                RootNavGraph(navController, userViewModel, workoutViewModel)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(serviceIntent)
    }
}