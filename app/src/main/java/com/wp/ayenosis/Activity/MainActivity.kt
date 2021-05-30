package com.wp.ayenosis.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.wp.ayenosis.R
import com.wp.ayenosis.databinding.ActivityLoginBinding
import com.wp.ayenosis.databinding.ActivityMainBinding
import com.wp.ayenosis.utils.FirebaseUtils
import com.wp.ayenosis.utils.FirebaseUtils.firebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Temporary for testing
        binding.logoSplashscreen.setOnClickListener{
            firebaseAuth.signOut()

            val mIntent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(mIntent)
            Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}