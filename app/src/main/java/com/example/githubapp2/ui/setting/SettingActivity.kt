package com.example.githubapp2.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.githubapp2.R
import com.example.githubapp2.databinding.ActivitySettingBinding
import com.google.android.material.switchmaterial.SwitchMaterial


private lateinit var binding: ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val switchTheme = binding.switchTheme

        val pref = SettingPreferences.getInstance(application.dataStore)
        val SettingViewModel =
            ViewModelProvider(this, SettingViewModelFactory(pref))[SettingViewModel::class.java]

        SettingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }

        }
        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            SettingViewModel.saveThemeSetting(isChecked)
        }
    }
}