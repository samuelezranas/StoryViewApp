package com.dicoding.storyviewapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.storyviewapp.R
import com.dicoding.storyviewapp.utils.ViewModelFactory
import com.dicoding.storyviewapp.adapter.MainAdapter
import com.dicoding.storyviewapp.data.response.ListStoryItem
import com.dicoding.storyviewapp.databinding.ActivityMainBinding
import com.dicoding.storyviewapp.ui.start.LandingActivity
import com.dicoding.storyviewapp.ui.upload.UploadActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getSession()
        showStory()

        binding.addStory.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getSession() {
        showLoading(true)
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                val intent = Intent(this, LandingActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            } else {
                showLoading(true)
                viewModel.listStory().observe(this) { stories ->
                    showLoading(false)
                    if (stories != null) {
                        showStoryList(stories)
                    } else {
                        showToast(getString(R.string.story_error))
                    }
                }
            }
        }
    }

    private fun showStory() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvList.layoutManager = layoutManager
    }

    private fun showLoading(isLoading: Boolean) {
        binding.addStory.isEnabled = !isLoading
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showStoryList(stories: List<ListStoryItem>?) {
        val adapter = MainAdapter()
        adapter.submitList(stories)
        binding.rvList.adapter = adapter
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_change_language ->  {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                true
            }
            R.id.action_logout ->  {
                viewModel.logout()
                val intent = Intent(this, LandingActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}