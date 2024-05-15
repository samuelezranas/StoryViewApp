package com.dicoding.storyviewapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.storyviewapp.data.database.UserModel
import com.dicoding.storyviewapp.data.database.UserRepository
import com.dicoding.storyviewapp.data.remote.response.ListStoryItem
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