package com.wp.ayenosis.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.wp.ayenosis.R
import com.wp.ayenosis.databinding.ActivitySignupBinding
import com.wp.ayenosis.model.User
import com.wp.ayenosis.utils.FirebaseUtils.db
import com.wp.ayenosis.utils.FirebaseUtils.firebaseAuth
import com.wp.ayenosis.utils.FirebaseUtils.firebaseUser


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide();
        initSpinner()

        binding.tvLogin.setOnClickListener {
            val mIntent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(mIntent)
        }

        binding.btnRegister.setOnClickListener {
            userEmail = binding.etEmailSu.text.toString().trim()
            userPassword = binding.etPasswordSu.text.toString().trim()

            if (checkValidity()) {
                createAccount()
                storeData()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val user = firebaseUser
        user?.let {
            val mIntent = Intent(this@SignUpActivity, MainActivity::class.java)
            startActivity(mIntent)
        }
    }

    private fun initSpinner() {
        spinner = binding.spJnsSu
        ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,
            R.layout.spinner_list
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun checkEmpty(): Boolean =
        binding.etEmailSu.text.toString().trim().isNotEmpty() &&
                binding.etPasswordSu.text.toString().trim().isNotEmpty() &&
                binding.etUnameSu.text.toString().trim().isNotEmpty() &&
                binding.etAgeSu.text.toString().trim().isNotEmpty()


    private fun checkEmailValidity(): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()

    private fun checkPasswordValidity(): Boolean {
        if (userPassword.length > 16 || userPassword.length < 8) {
            return false
        }
        return true
    }

    private fun checkValidity(): Boolean {

        if(!checkEmpty()) {
            Toast.makeText(
                this,
                "There is still at least an empty field", Toast.LENGTH_SHORT
            )
                .show()
            return false
        }

        if(!checkEmailValidity()) {
            Toast.makeText(
                this,
                "Inputted email is not valid", Toast.LENGTH_SHORT
            )
                .show()
            return false
        }

        if(!checkPasswordValidity()) {
            Toast.makeText(
                this,
                "Password should be around 8 - 16 in length", Toast.LENGTH_SHORT
            )
                .show()
            return false
        }

        return true
    }

    private fun createAccount() {
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this, "Account has been created.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this, "An error has occurred",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun storeData() {
        val userData = User(
            binding.etUnameSu.text.toString().trim(),
            Integer.parseInt(binding.etAgeSu.text.toString()),
            spinner.selectedItem.toString()
        )

        firebaseUser?.uid?.let { db.collection("users").document(it).set(userData) }
        val mIntent = Intent(this@SignUpActivity, MainActivity::class.java)
        startActivity(mIntent)
        finish()
    }
}