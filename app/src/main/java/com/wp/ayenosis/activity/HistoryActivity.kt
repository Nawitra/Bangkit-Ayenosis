package com.wp.ayenosis.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wp.ayenosis.Adapter.HistoryAdapter
import com.wp.ayenosis.Adapter.QuestionAnswerAdapter
import com.wp.ayenosis.R
import com.wp.ayenosis.model.Detection
import com.wp.ayenosis.model.QuestionAnswer

class HistoryActivity : AppCompatActivity() {

    private lateinit var rvHistory: RecyclerView
    private var arrayListDetection: MutableList<Detection> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        supportActionBar?.hide()

        val tvTitle: TextView = findViewById(R.id.tv_title)
        tvTitle.text = "Saved History"
        rvHistory = findViewById(R.id.rv_history)
        rvHistory.setHasFixedSize(true)
        prepData()
        showRecyclerList()


        val btnBack: ImageView = findViewById(R.id.btn_back)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val menuNav: BottomNavigationView = findViewById(R.id.menu_navigation_history)
        menuNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.btn_history -> {
                    Toast.makeText(this, "You are already in the History", Toast.LENGTH_SHORT).show()
                }
                R.id.btn_question -> {
                    val mIntent = Intent(this@HistoryActivity, FaqActivity::class.java)
                    startActivity(mIntent)
                    finish()
                }
                R.id.btn_home -> {
                    val mIntent = Intent(this@HistoryActivity, MainActivity::class.java)
                    startActivity(mIntent)
                    finish()
                }
            }
            true}

    }

    private fun prepData() {
        TODO("Not yet implemented")
    }

    private fun showRecyclerList() {
        rvHistory.layoutManager = LinearLayoutManager(this)
        val historyAdapter = HistoryAdapter(arrayListDetection)
        rvHistory.adapter = historyAdapter
    }
}