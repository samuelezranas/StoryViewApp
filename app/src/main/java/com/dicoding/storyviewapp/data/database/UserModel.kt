package com.dicoding.storyviewapp.data.database

data class UserModel(
    val token: String,
    val name: String,
    val userId: String,
    val isLogin: Boolean = false
)