package com.wp.ayenosis.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wp.ayenosis.adapter.IntroPagerAdapter
import com.wp.ayenosis.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref: SharedPreferences = getSharedPreferences(
            "onboarding_complete", Context.MODE_PRIVATE)

        if(sharedPref.getBoolean("onboarding_complete", false)) {
            val mIntent = Intent(this@OnboardingActivity, LoginActivity::class.java)
            startActivity(mIntent)
            finish()
        } else {
            val pagerAdapter = IntroPagerAdapter(this@OnboardingActivity)
            binding.viewpagerOnboarding.adapter = pagerAdapter
            val editor: SharedPreferences.Editor = sharedPref.edit()
            editor.putBoolean("onboarding_complete", true)
            editor.apply()
        }
    }
}