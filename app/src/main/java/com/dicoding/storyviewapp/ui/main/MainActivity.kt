package com.dicoding.storyviewapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.storyviewapp.R
import com.dicoding.storyviewapp.adapter.LoadingStateAdapter
import com.dicoding.storyviewapp.adapter.MainAdapter
import com.dicoding.storyviewapp.databinding.ActivityMainBinding
import com.dicoding.storyviewapp.ui.main.start.LandingActivity
import com.dicoding.storyviewapp.ui.viewmodel.MainViewModel
import com.dicoding.storyviewapp.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    private var mainAdapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showRecyclerView()
        getSession()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val typeface = ResourcesCompat.getFont(this, R.font.diploma_regular)
        toolbar.post {
            try {
                val titleTextView = toolbar.getChildAt(0) as? TextView
                titleTextView?.typeface = typeface
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.addStory.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }
        binding.openMap.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showRecyclerView() {
        val mainLayoutManager = LinearLayoutManager(this)
        showLoading(true)
        binding.rvList.apply {
            layoutManager = mainLayoutManager
            setHasFixedSize(true)
            adapter = mainAdapter
        }
    }

    private fun getSession() {
        showLoading(true)
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LandingActivity::class.java))
                finish()
            } else {
                showLoading(false)
                getDataStories()
            }
        }
    }

    private fun getDataStories() {

        binding.rvList.adapter = mainAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter()
        )

        viewModel.listStory.observe(this) {
            mainAdapter.submitData(lifecycle, it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.addStory.isEnabled = !isLoading
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_change_language -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                finish()
                true  // Return true to indicate the item has been handled
            }
            R.id.action_theme -> {
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_logout -> {
                viewModel.logout()
                val intent = Intent(this, LandingActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
                true  // Return true to indicate the item has been handled
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}