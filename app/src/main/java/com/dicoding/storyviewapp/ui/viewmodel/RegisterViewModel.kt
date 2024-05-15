package com.dicoding.storyviewapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.storyviewapp.data.database.UserRepository

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    suspend fun register(name: String, email: String, password: String) = userRepository.register(name, email, password)
}