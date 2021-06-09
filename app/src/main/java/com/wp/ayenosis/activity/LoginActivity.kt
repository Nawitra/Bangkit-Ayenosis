package com.wp.ayenosis.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.wp.ayenosis.databinding.ActivityLoginBinding
import com.wp.ayenosis.utils.FirebaseUtils.firebaseAuth
import com.wp.ayenosis.utils.FirebaseUtils.firebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userEmail: String
    private lateinit var userPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.tvRegister.setOnClickListener {
            val mIntent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(mIntent)
        }

        binding.loginBtn.setOnClickListener {
            if (checkEmpty()) {
                signIn()
            } else {
                Toast.makeText(
                    this,
                    "Please input valid email and password",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun checkEmpty(): Boolean = with(binding) {
        etEmailLi.text.toString().trim().isNotEmpty() &&
                etPasswordLi.text.toString().trim().isNotEmpty()
    }

    private fun signIn() {
        userEmail = binding.etEmailLi.text.toString().trim()
        userPassword = binding.etPasswordLi.text.toString().trim()

        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener { signIn ->
                if (signIn.isSuccessful) {
                    val mIntent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(mIntent)
                    Toast.makeText(
                        this, "Successfully logged in!",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this, "Login failed.", Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        val user = firebaseUser
        user?.let {
            val mIntent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(mIntent)
        }
    }
}