package com.dicoding.storyviewapp.ui.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.dicoding.storyviewapp.R
import com.dicoding.storyviewapp.utils.ViewModelFactory
import com.dicoding.storyviewapp.data.remote.response.RegisterResponse
import com.dicoding.storyviewapp.databinding.ActivityRegisterBinding
import com.dicoding.storyviewapp.ui.viewmodel.RegisterViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterActivity : AppCompatActivity() {

    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(false)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            showLoading(true)
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            when {
                name.isEmpty() -> {
                    showLoading(false)
                    binding.nameEditTextLayout.error = getString(R.string.null_name)
                }
                email.isEmpty() -> {
                    showLoading(false)
                    binding.emailEditTextLayout.error = getString(R.string.null_email)
                }
                password.isEmpty() -> {
                    showLoading(false)
                    binding.passwordEditTextLayout.error = getString(R.string.null_password)
                }
                else -> {
                    showLoading(true)
                    lifecycleScope.launch {
                        try {
                            val response = viewModel.register(name, email, password)
                            showLoading(false)
                            showToast(response.message)

                            // Inflate custom layout containing LottieAnimationView
                            val customLayout = LayoutInflater.from(this@RegisterActivity)
                                .inflate(R.layout.custom_dialog_layout, null)

                            // Initialize LottieAnimationView
                            val animationView =
                                customLayout.findViewById<LottieAnimationView>(R.id.animationView)
                            animationView.setAnimation("confirm.json")
                            animationView.playAnimation()

                            // Build AlertDialog with custom layout
                            AlertDialog.Builder(this@RegisterActivity).apply {
                                setTitle("Good News!")
                                setMessage(getString(R.string.success_registration))
                                setView(customLayout) // Set custom layout containing LottieAnimationView
                                setPositiveButton(getString(R.string.next)) { _, _ ->
                                    val intent = Intent(context, LoginActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                            }
                        } catch (e: HttpException) {
                            showLoading(false)
                            val errorBody = e.response()?.errorBody()?.string()
                            val errorResponse =
                                Gson().fromJson(errorBody, RegisterResponse::class.java)
                            showToast(errorResponse.message)
                        }

                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.signupButton.isEnabled = !isLoading
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA, 1f).setDuration(100)
        val nameTextView =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(100)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup
            )
            startDelay = 100
        }.start()
    }
}