package com.dicoding.storyviewapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.storyviewapp.data.repository.UserRepository

class MapsViewModel(private val userRepository: UserRepository): ViewModel() {
    fun getStoryLocation() = userRepository.getStoriesWithLocation()

}