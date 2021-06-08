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
import com.wp.ayenosis.Adapter.QuestionAnswerAdapter
import com.wp.ayenosis.R
import com.wp.ayenosis.model.QuestionAnswer

class FaqActivity : AppCompatActivity() {

    private lateinit var rvQuestion: RecyclerView
    private var arrayListQuestion: MutableList<QuestionAnswer> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)

        supportActionBar?.hide()

        val tvTitle: TextView = findViewById(R.id.tv_title)
        tvTitle.text = "Frequently Asked Question"
        rvQuestion = findViewById(R.id.rv_question)
        rvQuestion.setHasFixedSize(true)
        prepData()
        showRecyclerList()

        val btnBack: ImageView = findViewById(R.id.btn_back)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val menuNav1: BottomNavigationView = findViewById(R.id.menu_navigation1)
        menuNav1.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.btn_home -> {
                    val mIntent = Intent(this@FaqActivity, MainActivity::class.java)
                    startActivity(mIntent)
                    finish()
                }
                R.id.btn_question -> {
                    Toast.makeText(this, "You are already in the FAQ SCREEN", Toast.LENGTH_SHORT).show()
                }
                R.id.btn_history -> {
                    val mIntent = Intent(this@FaqActivity, HistoryActivity::class.java)
                    startActivity(mIntent)
                    finish()
                }
            }
            true}

    }

    private fun showRecyclerList() {
        rvQuestion.layoutManager = LinearLayoutManager(this)
        val questionAnswerAdapter = QuestionAnswerAdapter(arrayListQuestion)
        rvQuestion.adapter = questionAnswerAdapter
    }

    private fun prepData(){
        val questionList = resources.getStringArray(R.array.question)
        val answerList = resources.getStringArray(R.array.answer)

        arrayListQuestion.clear()
        for (i in questionList.indices) {
            val qa = QuestionAnswer()
            qa.question = questionList[i]
            qa.answer = answerList[i]

            arrayListQuestion.add(qa)
        }
    }

}