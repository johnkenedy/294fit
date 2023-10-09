package com.example.gym2.domain

import com.example.gym2.util.Resource
import com.google.firebase.auth.AuthResult

interface UserRepository {

    suspend fun createNewUser(
        userName: String,
        userEmailAddress: String,
        userLoginPassword: String
    ): Resource<AuthResult>

    suspend fun loginUser(email: String, password: String): Resource<AuthResult>

    suspend fun logOutUser()
}