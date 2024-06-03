package com.dicoding.storyviewapp.data.repository

data class UserModel(
    val token: String,
    val name: String,
    val userId: String,
    val isLogin: Boolean = false
)