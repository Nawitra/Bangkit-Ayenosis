package com.wp.ayenosis

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    private val delay: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        supportActionBar?.hide()

        val bgImg: ImageView = findViewById(R.id.logo_splashscreen)
        val animation = AnimationUtils.loadAnimation(this, R.anim.slide_animation)
        bgImg.startAnimation(animation)

        Handler(Looper.getMainLooper()).postDelayed(Runnable() {
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, delay)
    }
}