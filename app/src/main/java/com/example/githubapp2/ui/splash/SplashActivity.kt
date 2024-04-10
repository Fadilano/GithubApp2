package com.example.githubapp2.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.githubapp2.R
import com.example.githubapp2.databinding.ActivitySplashBinding
import com.example.githubapp2.ui.main.MainActivity
import com.example.githubapp2.ui.setting.SettingPreferences
import com.example.githubapp2.ui.setting.SettingViewModel
import com.example.githubapp2.ui.setting.SettingViewModelFactory
import com.example.githubapp2.ui.setting.dataStore

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageView.alpha = 0f
        binding.imageView.animate().setDuration(2000).alpha(1f).withEndAction{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}