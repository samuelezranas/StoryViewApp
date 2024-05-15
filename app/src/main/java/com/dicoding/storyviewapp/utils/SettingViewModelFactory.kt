package com.dicoding.storyviewapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.storyviewapp.data.datastore.SettingPreferences
import com.dicoding.storyviewapp.ui.viewmodel.SettingViewModel

class SettingViewModelFactory (private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown SettingViewModel class: " + modelClass.name)
    }
}