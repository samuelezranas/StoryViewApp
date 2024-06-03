package com.dicoding.storyviewapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.storyviewapp.data.repository.UserRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadViewModel(private val repository: UserRepository) : ViewModel() {

    fun uploadStory(file: MultipartBody.Part, description: RequestBody) {
        return repository.uploadStory(file, description)
    }
}