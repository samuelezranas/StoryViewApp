package com.dicoding.storyviewapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.storyviewapp.data.remote.response.ListStoryItem
import com.dicoding.storyviewapp.databinding.ActivityDetailStoryBinding

@Suppress("DEPRECATION")
class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stories = intent.getParcelableExtra<ListStoryItem>(DETAIL_STORY) as ListStoryItem

        showDetail(stories)
    }

    private fun showDetail(stories: ListStoryItem) {
        Glide.with(applicationContext)
            .load(stories.photoUrl)
            .into(binding.ivDetail)
        binding.nameDetail.text = stories.name
        binding.descDetail.text = stories.description
    }

    companion object {
        const val DETAIL_STORY = "detail_story"
    }
}