package com.example.gym2.ui.composables.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gym2.ui.composables.home.Title
import com.example.gym2.ui.theme.darkBlue

@Composable
fun ProfileScreen() {

    Surface(modifier = Modifier.fillMaxSize(), color = darkBlue) {
        Title(text = "coming soon", modifier = Modifier.fillMaxSize())
    }

}