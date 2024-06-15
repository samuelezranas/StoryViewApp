package com.dicoding.storyviewapp.di

import android.content.Context
import com.dicoding.storyviewapp.data.database.StoryRoomDatabase
import com.dicoding.storyviewapp.data.datastore.UserPreference
import com.dicoding.storyviewapp.data.datastore.UserRepository
import com.dicoding.storyviewapp.data.datastore.UserDataStore
import com.dicoding.storyviewapp.data.remote.api.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {fun provideRepository(context: Context): UserRepository {
    val pref = UserPreference.getInstance(context.UserDataStore)
    val user = runBlocking { pref.getSession().first() }
    val apiService = ApiConfig.getApiService(user.token)
    val storyRoomDatabase = StoryRoomDatabase.getDatabase(context)
    return UserRepository.getInstance(apiService, pref, storyRoomDatabase)
}
}