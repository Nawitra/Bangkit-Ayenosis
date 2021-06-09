package com.wp.ayenosis.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wp.ayenosis.R
import com.wp.ayenosis.databinding.ActivityMainBinding
import com.wp.ayenosis.utils.FirebaseUtils.firebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var name: String
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val user = firebaseAuth.currentUser
        if (user != null) {
            name = user.email.toString()
        }

        val tvHello: TextView = findViewById(R.id.tv_hello)
        tvHello.text = name

        val btnLogout: Button = findViewById(R.id.button_log_out)
        btnLogout.setOnClickListener{
            firebaseAuth.signOut()
            val mIntent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(mIntent)
            Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show()
            this.finish()
        }

        binding.btnScan.setOnClickListener{
            val mIntent = Intent(this@MainActivity, ChooseUploadActivity::class.java)
            startActivity(mIntent)
        }

        val menuNav: BottomNavigationView = findViewById(R.id.menu_navigation)
        menuNav.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.btn_home -> {
                        Toast.makeText(this, "You are already in the HOME SCREEN", Toast.LENGTH_SHORT).show()
                    }
                    R.id.btn_question -> {
                        val mIntent = Intent(this@MainActivity, FaqActivity::class.java)
                        startActivity(mIntent)
                        finish()
                    }
                    R.id.btn_history -> {
                        val mIntent = Intent(this@MainActivity, HistoryActivity::class.java)
                        startActivity(mIntent)
                        finish()
                    }
                }
                true}

    }
}