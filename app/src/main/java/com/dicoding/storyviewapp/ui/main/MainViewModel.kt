package com.dicoding.storyviewapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.storyviewapp.data.UserModel
import com.dicoding.storyviewapp.data.UserRepository
import com.dicoding.storyviewapp.data.response.ListStoryItem
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    fun listStory(): LiveData<List<ListStoryItem>?> {
        repository.listStory()
        return repository.listStory
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}