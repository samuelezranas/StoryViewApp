package com.dicoding.storyviewapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.storyviewapp.data.UserModel
import com.dicoding.storyviewapp.data.UserRepository
import com.dicoding.storyviewapp.data.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel()  {

    var login : MutableLiveData<LoginResponse> = repository.login

    fun login(email: String, password: String) = repository.login(email, password)

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}