package com.dicoding.storyviewapp.di

import android.content.Context
import com.dicoding.storyviewapp.data.UserPreference
import com.dicoding.storyviewapp.data.UserRepository
import com.dicoding.storyviewapp.data.dataStore
import com.dicoding.storyviewapp.data.remote.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {fun provideRepository(context: Context): UserRepository {
    val pref = UserPreference.getInstance(context.dataStore)
    val user = runBlocking { pref.getSession().first() }
    val apiService = ApiConfig.getApiService(user.token)
    return UserRepository.getInstance(apiService, pref)
    }
}